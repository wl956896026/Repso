package com.hr.dao.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hr.dao.EmpDao;
import com.hr.dao.impl.EmpDaoImpl;
import com.hr.entity.Emp;
import com.hr.entity.Experience;

public class EmpTest {

	@Test
	public void test() {
			EmpDao empDao = new EmpDaoImpl();
			Emp emp = new Emp();
			emp.setEmp_birthday("1990-10-10");
			emp.setEmp_email("1234543@ewrt");
			emp.setEmp_major("计算机");
			emp.setEmp_name("翠花2");
			emp.setEmp_phone("110");
			emp.setEmp_photo("234");
			emp.setEmp_put_time("2018-1-10");
			emp.setEmp_school("宝大");
			emp.setEmp_sex(0);
			emp.setEmp_xueli("本科");
			emp.setLogin_name("huahua2");
			emp.setLogin_password("123456");
			emp.setPosition_id(1);
			
			Experience ex = new Experience();
			ex.setCompany("xxx");
			ex.setStart_time("2017-10-10");
			ex.setEnd_time("2018-1-9");
			ex.setPosition("开发");
			ex.setJob_content("开发开发11111");
			int i=empDao.addEmp(emp, ex);
			if(i>0){
				System.out.println("ok");
			}else{
				System.out.println("no!ok");
			}
	}

}
