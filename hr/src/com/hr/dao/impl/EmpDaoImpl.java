package com.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hr.dao.DBUtil;
import com.hr.dao.EmpDao;
import com.hr.entity.Emp;
import com.hr.entity.Experience;

public class EmpDaoImpl extends DBUtil implements EmpDao {

	@Override
	public Emp empLogin(String loginName, String loginPassword) {
		String sql = "select emp_id,emp_name,position_id,account_state from tbl_emp where login_name=? and login_password=?";
		List<Emp> emps = super.executeQuery(sql, Emp.class, loginName,
				loginPassword);
		if (emps != null && emps.size() != 0) {
			return emps.get(0);
		}
		return null;
	}

	@Override
	public List<Object[]> queryEmp() {
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.emp_sex,e.emp_birthday,e.emp_phone,e.emp_put_time,"
				+ "		p.position_name,d.dept_name,e.account_state from tbl_emp e "
				+ "		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id where e.emp_state=0";
		
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			
			
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

	@Override
	public int addEmp(Emp emp, Experience experience) {
		// 添加员工基本资料
		String empSQL = "insert into tbl_emp values(default,?,?,'000000',?,?,?,?,?,?,?,?,?,?,?,?,0,0)";
		// 添加工作经验
		String experSQL = "insert into tbl_experience values(default,?,?,?,?,?)";
		try {
			conn = super.getConn();
			// 设置jdbc默认的事务提交方式为不自动提交
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(experSQL);
			ps.setString(1, experience.getStart_time());
			ps.setString(2, experience.getEnd_time());
			ps.setString(3, experience.getCompany());
			ps.setString(4, experience.getPosition());
			ps.setString(5, experience.getJob_content());
			int i = ps.executeUpdate();
			ps.close();
			// 添加员工基本资料
			if (i > 0) {
				Integer expericeId = null;
				String lastIdSQL = "select last_insert_id()";
				ps = conn.prepareStatement(lastIdSQL);
				rs = ps.executeQuery();
				if (rs.next()) {
					expericeId = rs.getInt(1);
				}
				ps.close();
				rs.close();

				ps = conn.prepareStatement(empSQL);
				ps.setString(1, emp.getEmp_name());
				ps.setString(2, emp.getLogin_name());
				ps.setString(3, emp.getEmp_photo());
				ps.setString(4, emp.getEmp_new_photo());
				ps.setInt(5, emp.getEmp_sex());
				ps.setString(6, emp.getEmp_birthday());
				ps.setString(7, emp.getEmp_xueli());
				ps.setString(8, emp.getEmp_major());
				ps.setString(9, emp.getEmp_school());
				ps.setString(10, emp.getEmp_phone());
				ps.setString(11, emp.getEmp_email());
				// 工作经验编号
				ps.setInt(12, expericeId);
				ps.setString(13, emp.getEmp_put_time());
				ps.setInt(14, emp.getPosition_id());

				i = ps.executeUpdate();
				if (i > 0) {
					conn.commit();// 事务提交
					return 1;
				} else {
					conn.rollback();// 事务回滚
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			super.closeAll();
		}

		return 0;
	}

	@Override
	public String[] queryEmpById(int empId) {
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.login_name,e.emp_sex,e.emp_birthday," +
				"		e.emp_xueli,e.emp_major,e.emp_school,e.emp_phone,e.emp_email," +
				"		e.emp_put_time,p.position_name,d.dept_name,e.account_state," +
				"		e.emp_state,ex.start_time,ex.end_time,ex.company,ex.position," +
				"		ex.job_content,e.emp_new_photo from tbl_emp e " +
				"		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id"
				+ "		inner join tbl_experience ex on e.experience_id=ex.experience_id"
				+ "		 where e.emp_id=?";
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			rs = ps.executeQuery();
			String[] empInfo = new String[rs.getMetaData().getColumnCount()];
			if (rs.next()) {

				// 循环从结果集中获取每一列数据，并填充到数组中
				for (int i = 0; i < empInfo.length; i++) {
					empInfo[i] = rs.getString(i + 1);
				}
				return empInfo;
			}
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
	/*	List<Emp> list = new EmpDaoImpl().queryEmpName("小");
		for(Emp emp : list){
			
			System.out.println(emp.getEmp_name());
		}*/
	}

	@Override
	public List<Object[]> queryEmp(String empName) {
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.emp_sex,e.emp_birthday,e.emp_phone,e.emp_put_time,"
				+ "		p.position_name,d.dept_name,e.account_state from tbl_emp e "
				+ "		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id where e.emp_state=0 and e.emp_name like ?";
		
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+empName+"%");
			
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

	@Override
	public boolean checkLoginName(String loginName) {
		String sql = "select count(*) as emp_id from tbl_emp where login_name=?";
		List<Emp> emps = super.executeQuery(sql, Emp.class, loginName);
		if(emps!=null && emps.size()!=0){
			if(emps.get(0).getEmp_id()==0){
				return true;
			}
		}
		return false;
	}

	@Override
	public String[] queryEmpName(String empName) {
		String sql = "select emp_name from tbl_emp where emp_name like ?";
		List<Emp> emps = super.executeQuery(sql, Emp.class, "%"+empName+"%");
		String[] empNames = new String[emps.size()];
		for(int i=0;i<emps.size();i++){
			Emp emp = emps.get(i);
			empNames[i] = emp.getEmp_name();
		}
		return empNames;
	}

}
