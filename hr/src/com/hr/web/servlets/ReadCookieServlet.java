package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***��ȡCookie
 * 
 * @author Administrator
 *
 */
public class ReadCookieServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��request�����л�ȡCookie
		Cookie[] cookies = request.getCookies();
		System.out.println(cookies.length);
		//����cookies���ÿ��Cookie
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("username")){
				System.out.println(cookie.getName()+":"+cookie.getValue());
			}else if(cookie.getName().equals("password")){
				System.out.println(cookie.getName()+":"+cookie.getValue());
			}
			//System.out.println(cookie.getName()+":"+cookie.getValue());
		}
	}
}
