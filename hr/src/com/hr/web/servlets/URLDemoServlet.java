package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLDemoServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得客户端提交的数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+"   "+password);
		request.getRequestDispatcher("result.jsp?username="+username+"&password="+password).forward(request, response);
		//response.sendRedirect("result.jsp?username="+username+"&password="+password);
		
	}
}
