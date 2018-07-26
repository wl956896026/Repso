package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.entity.Dept;

public class ELDemo3Servlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Dept dept = new Dept();
		dept.setDept_id(1);
		dept.setDept_name("ÑÐ·¢²¿");
		request.setAttribute("dept", dept);
		request.getRequestDispatcher("elDemo3.jsp").forward(request, response);
		
	}
}
