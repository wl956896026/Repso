package com.hr.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.DeptDao;
import com.hr.dao.impl.DeptDaoImpl;
import com.hr.entity.Dept;

@WebServlet(urlPatterns = "/dept/dept.do")
public class DeptServlet extends BaseServlet {
	private DeptDao deptDao = new DeptDaoImpl();

	/***
	 * ������в���
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryDept(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//��������
		PrintWriter out = response.getWriter();
		List<Dept> depts = deptDao.queryDept();
		// ��depts����ת��Ϊһ��JSON����
		StringBuffer jsonDept = new StringBuffer("[");
		for (Dept dept : depts) {
			jsonDept.append("{");
			jsonDept.append("dept_id").append(":").append(dept.getDept_id())
					.append(",").append("dept_name").append(":").append("\"")
					.append(dept.getDept_name()).append("\"");
			jsonDept.append("},");
		}
		//�Ƴ����һ������
		jsonDept.delete(jsonDept.length()-1, jsonDept.length());
		jsonDept.append("]");
		//��JSON��ʽ�����鴫���ͻ���
		out.print(jsonDept.toString());

	}
}
