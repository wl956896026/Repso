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
	 * ����¼���Ƿ����
	 * @param loginName
	 * @return
	 */
	public boolean checkLoginName(String loginName);
	/***
	 * ģ����ѯ���Ա����
	 * @param empName
	 * @return
	 */
	public String[] queryEmpName(String empName);
}
