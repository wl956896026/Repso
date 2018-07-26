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
	//2.���õײ�����й��ܴ���
	private EmpDao empDao = new EmpDaoImpl();
	/***
	 * ʵ�ֵ�¼���ܵ�Servlet����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ִ��login����....");
		//1.��ÿͻ�������
//				request.setCharacterEncoding("UTF-8");//���Ǳ��뼯
				String loginName = request.getParameter("loginName");
				String loginPassword = request.getParameter("loginPassword");
				
				
				
				//���õ�¼�������е�¼
				Emp emp = empDao.empLogin(loginName, loginPassword);
				//�ж�emp�Ƿ�Ϊnull
				if(emp!=null){
					//�жϸ��˻��Ƿ����
					if(emp.getAccount_state()==1){
						request.setAttribute("falSEMSG", "���˻��ѱ����ᣬ�������²�����ϵ");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}else if(emp.getAccount_state()==2){
						request.setAttribute("falSEMSG", "���˻���Ч");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}else if(emp.getAccount_state()==0){
						
						//��õ�¼Ա����ְλ��Ϣ
						PositionDao positionDao = new PositionDaoImpl();
						Position position = positionDao.queryPositionById(emp.getPosition_id());
						//request.getSession().setAttribute("aaa", "xxx");
						//���û���Ϣ���뵽session��
						request.getSession().setAttribute("emp", emp);
						//��ְλ������뵽session��
						request.getSession().setAttribute("position", position);
						
						
						//��ȡ7���Զ���¼��ѡ��
						String day = request.getParameter("day");
						if(day!=null && !day.equals("")){//�û���ѡ��7���Զ���¼
							//������¼����cookie
							Cookie usernameCookie = new Cookie("loginName",loginName);
							//���������cookie
							Cookie passwordCooie = new Cookie("loginPassword",loginPassword);
							//��������Cookie���������
							usernameCookie.setMaxAge(Integer.parseInt(day)*24*60*60);
							passwordCooie.setMaxAge(Integer.parseInt(day)*24*60*60);
							//������Cookie��ӵ�response������
							response.addCookie(usernameCookie);
							response.addCookie(passwordCooie);
						}
						
						
						
						
						//���ݵ�¼Ա���Ĳ�ְͬλ���뵽��ͬ����
						if(emp.getPosition_id()==4){//����רԱ
							//response.sendRedirect("manager.jsp");
							//������ѯԱ����Ϣ��Servlet
							request.getRequestDispatcher("emp/emp.do?operate=queryEmpPagin").forward(request, response);
						}else{//����������Ա
							response.sendRedirect("other.jsp");
						}
					}
				}else{
					request.setAttribute("falSEMSG", "�˻��������������������!");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
	}
	
	/***
	 * �Զ���¼Servlet
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void autoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ִ��autoLogin����....");
		//1.��ȡcookie�д�ŵ�����
				Cookie[] cookies = request.getCookies();
				String loginName = null;
				String loginPassword = null;
				if(cookies!=null && cookies.length!=0){
					//2.����Ƿ�����û���¼������
					for(Cookie cookie : cookies){
						//����¼���Ƿ����cookie��
						if(cookie.getName().equals("loginName")){
							loginName = cookie.getValue();//��õ�¼��
						}else if(cookie.getName().equals("loginPassword")){//����¼�����Ƿ����cookie��
							loginPassword = cookie.getValue();
						}
					}
					
					//�жϵ�¼���������Ƿ����
					if(loginName!=null && loginPassword!=null && !loginName.equals("") && !loginPassword.equals("")){
						//��ת��loginServlet���е�¼��ͬʱ����¼�������봫��
						request.getRequestDispatcher("account.do?operate=login&loginName="+loginName+"&loginPassword="+loginPassword).forward(request, response);
						
					}else{//������¼ҳ��
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				}else{//������¼ҳ��
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
	}
	
	/**
	 * �˳�Servlet����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����session��Ҫ��֤session��ֻ�洢��¼�û���������ݣ���û����������
		//request.getSession().invalidate();
		//��ʽ2����session���û���������Ƴ�
		request.getSession().removeAttribute("emp");
		request.getSession().removeAttribute("position");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length!=0){
			for(Cookie cookie :cookies){
				//����¼���Ƿ����cookie��
				if(cookie.getName().equals("loginName")){
					cookie.setMaxAge(0);//����Ϊ0ֱ�Ӵ�cookie���Ƴ�������Ϊ-1�������������
					response.addCookie(cookie);
				}else if(cookie.getName().equals("loginPassword")){//����¼�����Ƿ����cookie��
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		response.sendRedirect("index.jsp");
	}
}
