package com.hr.dao;

import java.sql.SQLException;

public class DBTest extends DBUtil{
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBTest test = new DBTest();
		long start= System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			test.getConn();
			test.closeAll();
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start);
	}
}
