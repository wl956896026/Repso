package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HideServlet extends HttpServlet {
@Override
protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	//如果当前存在则直接获取，如果当前不存在则创建一个新session
	HttpSession session= request.getSession();
	//如果参数为true则和request.getSession()一样，如果参数为false则表示，如果当前存在session直接获取，不存在则返回null
	HttpSession session1 = request.getSession(false|true);
	String username = request.getParameter("username");
	String id = request.getParameter("id");
	System.out.println(username+"   "+id  );
}
}
