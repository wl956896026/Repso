package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.EmpDao;
import com.hr.dao.PositionDao;
import com.hr.dao.impl.EmpDaoImpl;
import com.hr.dao.impl.PositionDaoImpl;
import com.hr.entity.Emp;
import com.hr.entity.Position;

@WebServlet(urlPatterns="/account.do")
public class AccountServlet extends BaseServlet {
	//2.调用底层类进行功能处理
	private EmpDao empDao = new EmpDaoImpl();
	/***
	 * 实现登录功能的Servlet方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行login方法....");
		//1.获得客户端请求
//				request.setCharacterEncoding("UTF-8");//这是编码集
				String loginName = request.getParameter("loginName");
				String loginPassword = request.getParameter("loginPassword");
				
				
				
				//调用登录方法进行登录
				Emp emp = empDao.empLogin(loginName, loginPassword);
				//判断emp是否为null
				if(emp!=null){
					//判断该账户是否可用
					if(emp.getAccount_state()==1){
						request.setAttribute("falSEMSG", "该账户已被冻结，请于人事部门联系");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}else if(emp.getAccount_state()==2){
						request.setAttribute("falSEMSG", "该账户无效");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}else if(emp.getAccount_state()==0){
						
						//获得登录员工的职位信息
						PositionDao positionDao = new PositionDaoImpl();
						Position position = positionDao.queryPositionById(emp.getPosition_id());
						//request.getSession().setAttribute("aaa", "xxx");
						//将用户信息存入到session中
						request.getSession().setAttribute("emp", emp);
						//将职位对象存入到session中
						request.getSession().setAttribute("position", position);
						
						
						//获取7天自动登录复选框
						String day = request.getParameter("day");
						if(day!=null && !day.equals("")){//用户勾选了7天自动登录
							//创建登录名的cookie
							Cookie usernameCookie = new Cookie("loginName",loginName);
							//创建密码的cookie
							Cookie passwordCooie = new Cookie("loginPassword",loginPassword);
							//设置两个Cookie的最大存活期
							usernameCookie.setMaxAge(Integer.parseInt(day)*24*60*60);
							passwordCooie.setMaxAge(Integer.parseInt(day)*24*60*60);
							//将两个Cookie添加到response对象中
							response.addCookie(usernameCookie);
							response.addCookie(passwordCooie);
						}
						
						
						
						
						//根据登录员工的不同职位进入到不同界面
						if(emp.getPosition_id()==4){//人事专员
							//response.sendRedirect("manager.jsp");
							//跳到查询员工信息的Servlet
							request.getRequestDispatcher("emp/emp.do?operate=queryEmpPagin").forward(request, response);
						}else{//其他工作人员
							response.sendRedirect("other.jsp");
						}
					}
				}else{
					request.setAttribute("falSEMSG", "账户或密码错误，请重新输入!");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
	}
	
	/***
	 * 自动登录Servlet
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void autoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行autoLogin方法....");
		//1.获取cookie中存放的数据
				Cookie[] cookies = request.getCookies();
				String loginName = null;
				String loginPassword = null;
				if(cookies!=null && cookies.length!=0){
					//2.检测是否存在用户登录的数据
					for(Cookie cookie : cookies){
						//检测登录名是否存在cookie中
						if(cookie.getName().equals("loginName")){
							loginName = cookie.getValue();//获得登录名
						}else if(cookie.getName().equals("loginPassword")){//检测登录密码是否存在cookie中
							loginPassword = cookie.getValue();
						}
					}
					
					//判断登录名和密码是否存在
					if(loginName!=null && loginPassword!=null && !loginName.equals("") && !loginPassword.equals("")){
						//跳转到loginServlet进行登录，同时将登录名和密码传入
						request.getRequestDispatcher("account.do?operate=login&loginName="+loginName+"&loginPassword="+loginPassword).forward(request, response);
						
					}else{//跳到登录页面
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}else{//跳到登录页面
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
	}
	
	/**
	 * 退出Servlet方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//销毁session，要保证session中只存储登录用户的相关数据，而没有其他数据
		//request.getSession().invalidate();
		//方式2，从session将用户相关数据移除
		request.getSession().removeAttribute("emp");
		request.getSession().removeAttribute("position");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length!=0){
			for(Cookie cookie :cookies){
				//检测登录名是否存在cookie中
				if(cookie.getName().equals("loginName")){
					cookie.setMaxAge(0);//设置为0直接从cookie中移除，设置为-1缓存在浏览器中
					response.addCookie(cookie);
				}else if(cookie.getName().equals("loginPassword")){//检测登录密码是否存在cookie中
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		response.sendRedirect("index.jsp");
	}
}
