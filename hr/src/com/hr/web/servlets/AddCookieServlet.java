package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * ���Cookie
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
		//����Cookie����,����cookie���key��value
		Cookie usernameCookie = new Cookie("username","admin");
		//����cookie��������ڣ�����Ϊ��λ��,Ĭ��cookie������session������
		usernameCookie.setMaxAge(7*24*60*60);
		Cookie passwordCookie = new Cookie("password","123456");
		passwordCookie.setMaxAge(7*24*60*60);
		//��cookie������ӵ��ͻ���
		response.addCookie(usernameCookie);
		response.addCookie(passwordCookie);
		response.sendRedirect("CookieDemo.jsp");
	}
}	
