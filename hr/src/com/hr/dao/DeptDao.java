package com.hr.dao;

import java.util.List;

import com.hr.entity.Dept;

public interface DeptDao {
	/***
	 * ��ȡ���в�����Ϣ
	 * @return
	 */
	public List<Dept> queryDept();
}
