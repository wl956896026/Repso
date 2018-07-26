package com.hr.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.DeptDao;
import com.hr.dao.PositionDao;
import com.hr.dao.impl.DeptDaoImpl;
import com.hr.dao.impl.PositionDaoImpl;
import com.hr.entity.Dept;
import com.hr.entity.Position;

public class QueryDeptAndPostion extends HttpServlet {
	private static final long serialVersionUID = -1758527581196559569L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DeptDao deptDao = new DeptDaoImpl();
		PositionDao positionDao = new PositionDaoImpl();
		List<Dept> depts = deptDao.queryDept();
		List<Position> positions = positionDao.queryPositions();
		request.setAttribute("depts", depts);
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("addEmp.jsp").forward(request, response);
	}
}
