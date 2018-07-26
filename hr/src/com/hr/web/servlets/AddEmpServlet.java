package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.EmpDao;
import com.hr.dao.impl.EmpDaoImpl;
import com.hr.entity.Emp;
import com.hr.entity.Experience;
@WebServlet(urlPatterns="/addEmp")//ServletÅäÖÃ×¢½â
public class AddEmpServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String emp_name = request.getParameter("emp_name");
		String emp_sex = request.getParameter("emp_sex");
		String login_name = request.getParameter("login_name");
		String emp_photo = request.getParameter("emp_photo");
		String emp_birthday = request.getParameter("emp_birthday");
		String emp_xueli = request.getParameter("emp_xueli");
		String emp_major = request.getParameter("emp_major");
		String emp_school = request.getParameter("emp_school");
		String emp_phone = request.getParameter("emp_phone");
		String emp_email = request.getParameter("emp_email");
		String emp_put_time = request.getParameter("emp_put_time");
		String position_id = request.getParameter("position_id");
		
		Emp emp = new Emp();
		emp.setEmp_birthday(emp_birthday);
		emp.setEmp_email(emp_email);
		emp.setEmp_major(emp_major);
		emp.setEmp_name(emp_name);
		emp.setEmp_phone(emp_phone);
		emp.setEmp_photo(emp_photo);
		emp.setEmp_put_time(emp_put_time);
		emp.setEmp_school(emp_school);
		emp.setEmp_sex(Integer.parseInt(emp_sex));
		emp.setEmp_xueli(emp_xueli);
		emp.setLogin_name(login_name);
		emp.setPosition_id(Integer.parseInt(position_id));
		
		
		
		
		
		
		
		String company = request.getParameter("company");
		String position = request.getParameter("position");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String job_content = request.getParameter("job_content");
		
		
		Experience experience = new Experience();
		experience.setCompany(company);
		experience.setEnd_time(end_time);
		experience.setJob_content(job_content);
		experience.setPosition(position);
		experience.setStart_time(start_time);
		
		
		
		EmpDao empDao = new EmpDaoImpl();
		int i = empDao.addEmp(emp, experience);
		if(i>0){
			response.sendRedirect("queryEmpServlet");
		}else{
			response.sendRedirect("queryDeptPosition");
		}
		
	}
}
