package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCookieServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("username")){
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
			}else if(cookie.getName().equals("password")){
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
			}
		}
		response.sendRedirect("CookieDemo.jsp");
	}
}
