package com.hr.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.dbcp.BasicDataSource;

/***
 * ���ݿ������
 * 
 * @author Administrator
 * @param <T>
 * 
 */
public abstract class DBUtil {
	protected Connection conn = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	private static String driverClass = null;
	private static String url = null;
	private static String username = null;
	private  static String password = null;
	//����һ������Դ����
	private static BasicDataSource dataSource = new BasicDataSource();
	static{
		setProperties();
		setDataSource();
	}
	/***
	 * ��������Դ(�������ӳ��������)
	 */
	public static void setDataSource(){
		//��������Դ����������
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		//�������ݿ����ӳ�����
		dataSource.setInitialSize(10);//��ʼ��������
		dataSource.setMaxActive(30);//�����������
		dataSource.setMaxIdle(8);//����������
		dataSource.setMinIdle(3);//��С��������
		dataSource.setMaxWait(1000);//���ȴ�ʱ��
		
	}
	/***
	 * ��ȡ��Դ�ļ�
	 */
	public static void setProperties(){
		try {
			//����Properties�����
			Properties proper = new Properties();
			//���db.properties�ļ���Ӧ��������
			InputStream input = DBUtil.class.getResourceAsStream("/db.properties");
			//��db.properties�ļ����ص��ڴ�
			proper.load(input);
			//����key��ö�Ӧ��value
			driverClass = proper.getProperty("conn_mysql_driver_class");
			url = proper.getProperty("conn_mysql_url");
			username = proper.getProperty("conn_mysql_username");
			password = proper.getProperty("conn_mysql_password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * ʹ�����ݿ����ӳ��������ݿ�
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn() throws ClassNotFoundException, SQLException {
		if(conn==null || conn.isClosed()){
			//��������
			conn = dataSource.getConnection();
		}
		return conn;
	}
	
	/***
	 * �������ݿⷽ��
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn1() throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		// 2.��������(ʹ��DriverManager����������)
		conn = DriverManager
				.getConnection(
						url,
						username, password);
		return conn;
	}

	/**
	 * �ͷ���Դ
	 */
	public void closeAll() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn = null;
			ps = null;
			rs = null;
		}
	}

	/***
	 * ִ����ɾ�ķ��� ����1��SQL��� ����2����ʾSQL����У���ռλ����ֵ��ʹ��Object�����ʾ return ���º��Ӱ������
	 */
	public int executeUpdate(String sql, Object[] params) {
		try {
			// ������Ӷ���
			this.getConn();
			// ����Ԥ�������
			ps = conn.prepareStatement(sql);
			// �ж�SQL������Ƿ����?��ռλ��
			if (params != null && params.length != 0) {
				// SQL����д��ڣ���ռλ�����������е�Ԫ�ظ�ֵ������ռλ��
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL��䲢����
			return ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ͷ���Դ
			this.closeAll();
		}
		return 0;
	}

	/***
	 * ��ѯ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param cla
	 *            ����װ������ֽ������
	 * @param params
	 *            SQL����У���ռλ���Ĳ���ֵ
	 * @return
	 */
	public <T> List<T> executeQuery(String sql, Class<T> cla, Object... params) {
		try {
			// ������Ӷ���
			this.getConn();
			// ����Ԥ�������
			ps = conn.prepareStatement(sql);
			// �ж�SQL������Ƿ����?��ռλ��
			if (params != null && params.length != 0) {
				// SQL����д��ڣ���ռλ�����������е�Ԫ�ظ�ֵ������ռλ��
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			//ִ��SQL���
			rs = ps.executeQuery();
			//��ý�����е�Ԫ����
			ResultSetMetaData rsmd = rs.getMetaData();
			//��ò�ѯ���������
			int columnCount = rsmd.getColumnCount();
			//���ڴ�����ݵ�list����
			List<T> list = new ArrayList<T>();
			while(rs.next()){
				//��������
				T t = cla.newInstance();
				//ѭ�����ÿ�е�����
				for(int i=1;i<=columnCount;i++){
					//���ĳ�е�ֵ
					Object value = rs.getObject(i);
					//��õ�ǰ�е�����
					String columnName = rsmd.getColumnName(i);
					//��������ƴ��Ϊset������
					String methodName = "set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);
					//��������������Զ���(����������Ҫ��һ��<��Сдһ��>)
					Field field = cla.getDeclaredField(columnName);
					//���ݷ������Ͳ���������(���Ե�����)��÷�������
					Method method = cla.getDeclaredMethod(methodName, field.getType());
					//���÷���,ʹ������ת��������õ�ֵ��������ת��
					method.invoke(t, ConvertUtils.convert(value, field.getType()));
					
				}
				//����װ�õĶ�����ӵ�list������
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			this.closeAll();
		}
		return null;
	}

}
