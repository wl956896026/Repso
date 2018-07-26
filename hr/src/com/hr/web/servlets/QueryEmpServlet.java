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
 * ��ȡ����Ա������
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
		//�������������
		EmpDao empDao = new EmpDaoImpl();
		List<Object[]> emps = empDao.queryEmp();
		//����õ��Ĵ���Ա�����ݵ�list���ϴ��뵽request��
		request.setAttribute("emps", emps);
		//����ת����manager.jspҳ��
		request.getRequestDispatcher("manager.jsp").forward(request, response);
		
	}
}
