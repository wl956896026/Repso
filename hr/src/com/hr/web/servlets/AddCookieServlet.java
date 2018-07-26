package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * 添加Cookie
 * @author Administrator
 *
 */
public class AddCookieServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6122230586009497864L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//创建Cookie对象,并向cookie添加key和value
		Cookie usernameCookie = new Cookie("username","admin");
		//设置cookie的最大存活期（以秒为单位）,默认cookie依赖于session而存在
		usernameCookie.setMaxAge(7*24*60*60);
		Cookie passwordCookie = new Cookie("password","123456");
		passwordCookie.setMaxAge(7*24*60*60);
		//将cookie对象添加到客户端
		response.addCookie(usernameCookie);
		response.addCookie(passwordCookie);
		response.sendRedirect("CookieDemo.jsp");
	}
}	
