package com.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hr.dao.DBUtil;
import com.hr.entity.Emp;

/***
 * 分页获取员工信息
 * @author Administrator
 *
 */
public class PaginEmp extends DBUtil{
	private int curPage;//当前页
	private int countPage;//总页数
	private final int PAGEROWNUM=5;//每页显示的行数
	/**
	 * 获得当前页
	 * @return
	 */
	public int getCurPage() {
		return curPage;
	}
	/***
	 * 设置当前页
	 * @param curPage
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	/***
	 * 获得总页数
	 * @return
	 */
	public int getCountPage() {
		return countPage;
	}
	/***
	 * 设置总页数
	 * @param countPage
	 */
	public void setCountPage() {
		String sql = "select count(*)  as emp_id  from tbl_emp where emp_state=0";
		List<Emp> emps = super.executeQuery(sql, Emp.class);
		if(emps!=null && emps.size()!=0){
			//为总页数属性设置值
			this.countPage = emps.get(0).getEmp_id()%this.PAGEROWNUM==0?emps.get(0).getEmp_id()/this.PAGEROWNUM:emps.get(0).getEmp_id()/this.PAGEROWNUM+1;
		}
	}
	/**8
	 * 获取每页显示的行数
	 * @return
	 */
	public int getPAGEROWNUM() {
		return PAGEROWNUM;
	}
	/***
	 * 分页查询员工信息
	 * @param pageNum
	 * @return
	 */
	public List<Object[]> queryEmp(int pageNum){
		//设置起始行数
		int startRow = (pageNum-1)*this.PAGEROWNUM;
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.emp_sex,e.emp_birthday,e.emp_phone,e.emp_put_time,"
				+ "		p.position_name,d.dept_name,e.account_state from tbl_emp e "
				+ "		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id where e.emp_state=0 limit ?,?";
		
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			//为SQL中设置起始行
			ps.setInt(1, startRow);
			//设置每页显示的行数
			ps.setInt(2, this.PAGEROWNUM);
			
			rs = ps.executeQuery();
			List<Object[]> empList = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] objs = new Object[9];
				// 循环从结果集中获取每一列数据，并填充到数组中
				for (int i = 0; i < objs.length; i++) {
					objs[i] = rs.getObject(i + 1);
				}
				// 将存有每一列数据的数组存入到list集合中
				empList.add(objs);
			}
			return empList;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return null;
	}
	
	
	
	/***
	 * 分页查询员工信息(根据员工名模糊查询)
	 * @param pageNum
	 * @return
	 */
	public List<Object[]> queryEmp(int pageNum,String empName){
		//设置起始行数
		int startRow = (pageNum-1)*this.PAGEROWNUM;
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.emp_sex,e.emp_birthday,e.emp_phone,e.emp_put_time,"
				+ "		p.position_name,d.dept_name,e.account_state from tbl_emp e "
				+ "		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id where e.emp_state=0 and emp_name like ? limit ?,?";
		
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+empName+"%");
			//为SQL中设置起始行
			ps.setInt(2, startRow);
			//设置每页显示的行数
			ps.setInt(3, this.PAGEROWNUM);
			
			rs = ps.executeQuery();
			List<Object[]> empList = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] objs = new Object[9];
				// 循环从结果集中获取每一列数据，并填充到数组中
				for (int i = 0; i < objs.length; i++) {
					objs[i] = rs.getObject(i + 1);
				}
				// 将存有每一列数据的数组存入到list集合中
				empList.add(objs);
			}
			return empList;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return null;
	}
	public static void main(String[] args) {
		PaginEmp paginEmp = new PaginEmp();
		List<Object[]> emps = paginEmp.queryEmp(2,"小");
		for(Object[] objs : emps){
			System.out.println(objs[0]+"   "+objs[1]);
		}
	}
	
}
