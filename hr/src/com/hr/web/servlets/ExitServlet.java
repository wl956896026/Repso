package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * 退出Servlet
 * @author Administrator
 *
 */
public class ExitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648351861339107437L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
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
