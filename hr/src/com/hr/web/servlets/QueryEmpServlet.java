package com.hr.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.EmpDao;
import com.hr.dao.impl.EmpDaoImpl;
/***
 * 获取所有员工资料
 * @author Administrator
 *
 */
public class QueryEmpServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3751814859735564905L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//创建处理类对象
		EmpDao empDao = new EmpDaoImpl();
		List<Object[]> emps = empDao.queryEmp();
		//将获得到的存有员工数据的list集合存入到request中
		request.setAttribute("emps", emps);
		//请求转发到manager.jsp页面
		request.getRequestDispatcher("manager.jsp").forward(request, response);
		
	}
}
