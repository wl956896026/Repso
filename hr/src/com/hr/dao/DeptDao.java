package com.hr.dao;

import java.util.List;

import com.hr.entity.Dept;

public interface DeptDao {
	/***
	 * 获取所有部门信息
	 * @return
	 */
	public List<Dept> queryDept();
}
