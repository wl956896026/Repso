package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ElDemo2Servlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1278744187180305715L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("name", "requestName");
		request.getSession().setAttribute("name", "sessionName");
		//获得一个application对象
		request.getServletContext().setAttribute("name", "applicationName");
		request.getRequestDispatcher("elDemo2.jsp").forward(request, response);
	}
}
