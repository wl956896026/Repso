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
 * 数据库帮助类
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
	//创建一个数据源对象
	private static BasicDataSource dataSource = new BasicDataSource();
	static{
		setProperties();
		setDataSource();
	}
	/***
	 * 配置数据源(配置连接池相关属性)
	 */
	public static void setDataSource(){
		//设置数据源的连接属性
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		//配置数据库连接池属性
		dataSource.setInitialSize(10);//初始化连接数
		dataSource.setMaxActive(30);//设置最大活动连接
		dataSource.setMaxIdle(8);//最大空闲连接
		dataSource.setMinIdle(3);//最小空闲连接
		dataSource.setMaxWait(1000);//最大等待时间
		
	}
	/***
	 * 读取资源文件
	 */
	public static void setProperties(){
		try {
			//创建Properties类对象
			Properties proper = new Properties();
			//获得db.properties文件对应的输入流
			InputStream input = DBUtil.class.getResourceAsStream("/db.properties");
			//将db.properties文件加载到内存
			proper.load(input);
			//根据key获得对应的value
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
	 * 使用数据库连接池连接数据库
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn() throws ClassNotFoundException, SQLException {
		if(conn==null || conn.isClosed()){
			//建立连接
			conn = dataSource.getConnection();
		}
		return conn;
	}
	
	/***
	 * 连接数据库方法
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn1() throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		// 2.建立连接(使用DriverManager来建立连接)
		conn = DriverManager
				.getConnection(
						url,
						username, password);
		return conn;
	}

	/**
	 * 释放资源
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
	 * 执行增删改方法 参数1：SQL语句 参数2：表示SQL语句中？号占位符的值，使用Object数组表示 return 更新后的影响行数
	 */
	public int executeUpdate(String sql, Object[] params) {
		try {
			// 获得连接对象
			this.getConn();
			// 创建预处理对象
			ps = conn.prepareStatement(sql);
			// 判断SQL语句中是否存在?号占位符
			if (params != null && params.length != 0) {
				// SQL语句中存在？号占位符，则将数组中的元素赋值给？号占位符
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			// 执行SQL语句并返回
			return ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 释放资源
			this.closeAll();
		}
		return 0;
	}

	/***
	 * 查询方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param cla
	 *            待封装对象的字节码对象
	 * @param params
	 *            SQL语句中？号占位符的参数值
	 * @return
	 */
	public <T> List<T> executeQuery(String sql, Class<T> cla, Object... params) {
		try {
			// 获得连接对象
			this.getConn();
			// 创建预处理对象
			ps = conn.prepareStatement(sql);
			// 判断SQL语句中是否存在?号占位符
			if (params != null && params.length != 0) {
				// SQL语句中存在？号占位符，则将数组中的元素赋值给？号占位符
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			//执行SQL语句
			rs = ps.executeQuery();
			//获得结果集中的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获得查询结果的列数
			int columnCount = rsmd.getColumnCount();
			//用于存放数据的list集合
			List<T> list = new ArrayList<T>();
			while(rs.next()){
				//创建对象
				T t = cla.newInstance();
				//循环获得每列的数据
				for(int i=1;i<=columnCount;i++){
					//获得某列的值
					Object value = rs.getObject(i);
					//获得当前列的列名
					String columnName = rsmd.getColumnName(i);
					//根据列名拼接为set方法名
					String methodName = "set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);
					//根据列名获得属性对象(列名和属性要求一致<大小写一致>)
					Field field = cla.getDeclaredField(columnName);
					//根据方法名和参数的类型(属性的类型)获得方法对象
					Method method = cla.getDeclaredMethod(methodName, field.getType());
					//调用方法,使用类型转换器将获得的值进行类型转换
					method.invoke(t, ConvertUtils.convert(value, field.getType()));
					
				}
				//将封装好的对象添加到list集合中
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
