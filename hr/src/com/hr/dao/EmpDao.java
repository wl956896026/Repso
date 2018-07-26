package com.hr.dao;

import java.util.List;

import com.hr.entity.Emp;
import com.hr.entity.Experience;

public interface EmpDao {
	public Emp empLogin(String loginName,String loginPassword);
	public List<Object[]> queryEmp(String empName);
	public List<Object[]> queryEmp();
	public int addEmp(Emp emp,Experience experience);
	public String[] queryEmpById(int empId);
	/***
	 * 检测登录名是否可用
	 * @param loginName
	 * @return
	 */
	public boolean checkLoginName(String loginName);
	/***
	 * 模糊查询获得员工名
	 * @param empName
	 * @return
	 */
	public String[] queryEmpName(String empName);
}
