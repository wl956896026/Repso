package com.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hr.dao.DBUtil;
import com.hr.dao.DeptDao;
import com.hr.entity.Dept;

public class DeptDaoImpl extends DBUtil implements DeptDao {
	@Override
	public List<Dept> queryDept() {
		String sql = "select * from tbl_dept";
		return super.executeQuery(sql, Dept.class);
	}
	public static void main(String[] args) {
		List<Dept> depts = new DeptDaoImpl().queryDept();
		for(Dept dept : depts){
			System.out.println(dept.getDept_id()+"   "+dept.getDept_name());
		}
	}

}
