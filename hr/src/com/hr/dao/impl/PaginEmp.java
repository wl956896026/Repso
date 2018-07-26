package com.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hr.dao.DBUtil;
import com.hr.entity.Emp;

/***
 * ��ҳ��ȡԱ����Ϣ
 * @author Administrator
 *
 */
public class PaginEmp extends DBUtil{
	private int curPage;//��ǰҳ
	private int countPage;//��ҳ��
	private final int PAGEROWNUM=5;//ÿҳ��ʾ������
	/**
	 * ��õ�ǰҳ
	 * @return
	 */
	public int getCurPage() {
		return curPage;
	}
	/***
	 * ���õ�ǰҳ
	 * @param curPage
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	/***
	 * �����ҳ��
	 * @return
	 */
	public int getCountPage() {
		return countPage;
	}
	/***
	 * ������ҳ��
	 * @param countPage
	 */
	public void setCountPage() {
		String sql = "select count(*)  as emp_id  from tbl_emp where emp_state=0";
		List<Emp> emps = super.executeQuery(sql, Emp.class);
		if(emps!=null && emps.size()!=0){
			//Ϊ��ҳ����������ֵ
			this.countPage = emps.get(0).getEmp_id()%this.PAGEROWNUM==0?emps.get(0).getEmp_id()/this.PAGEROWNUM:emps.get(0).getEmp_id()/this.PAGEROWNUM+1;
		}
	}
	/**8
	 * ��ȡÿҳ��ʾ������
	 * @return
	 */
	public int getPAGEROWNUM() {
		return PAGEROWNUM;
	}
	/***
	 * ��ҳ��ѯԱ����Ϣ
	 * @param pageNum
	 * @return
	 */
	public List<Object[]> queryEmp(int pageNum){
		//������ʼ����
		int startRow = (pageNum-1)*this.PAGEROWNUM;
		String sql = "select "
				+ "		e.emp_id,e.emp_name,e.emp_sex,e.emp_birthday,e.emp_phone,e.emp_put_time,"
				+ "		p.position_name,d.dept_name,e.account_state from tbl_emp e "
				+ "		inner join tbl_position p on e.position_id=p.position_id	"
				+ "		inner join tbl_dept d on p.dept_id=d.dept_id where e.emp_state=0 limit ?,?";
		
		try {
			super.getConn();
			ps = conn.prepareStatement(sql);
			//ΪSQL��������ʼ��
			ps.setInt(1, startRow);
			//����ÿҳ��ʾ������
			ps.setInt(2, this.PAGEROWNUM);
			
			rs = ps.executeQuery();
			List<Object[]> empList = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] objs = new Object[9];
				// ѭ���ӽ�����л�ȡÿһ�����ݣ�����䵽������
				for (int i = 0; i < objs.length; i++) {
					objs[i] = rs.getObject(i + 1);
				}
				// ������ÿһ�����ݵ�������뵽list������
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
	 * ��ҳ��ѯԱ����Ϣ(����Ա����ģ����ѯ)
	 * @param pageNum
	 * @return
	 */
	public List<Object[]> queryEmp(int pageNum,String empName){
		//������ʼ����
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
			//ΪSQL��������ʼ��
			ps.setInt(2, startRow);
			//����ÿҳ��ʾ������
			ps.setInt(3, this.PAGEROWNUM);
			
			rs = ps.executeQuery();
			List<Object[]> empList = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] objs = new Object[9];
				// ѭ���ӽ�����л�ȡÿһ�����ݣ�����䵽������
				for (int i = 0; i < objs.length; i++) {
					objs[i] = rs.getObject(i + 1);
				}
				// ������ÿһ�����ݵ�������뵽list������
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
		List<Object[]> emps = paginEmp.queryEmp(2,"С");
		for(Object[] objs : emps){
			System.out.println(objs[0]+"   "+objs[1]);
		}
	}
	
}
