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
	 * 获得所有部门
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryDept(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获得输出流
		PrintWriter out = response.getWriter();
		List<Dept> depts = deptDao.queryDept();
		// 将depts集合转换为一个JSON数组
		StringBuffer jsonDept = new StringBuffer("[");
		for (Dept dept : depts) {
			jsonDept.append("{");
			jsonDept.append("dept_id").append(":").append(dept.getDept_id())
					.append(",").append("dept_name").append(":").append("\"")
					.append(dept.getDept_name()).append("\"");
			jsonDept.append("},");
		}
		//移除最后一个逗号
		jsonDept.delete(jsonDept.length()-1, jsonDept.length());
		jsonDept.append("]");
		//将JSON格式的数组传到客户端
		out.print(jsonDept.toString());

	}
}
