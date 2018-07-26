package com.hr.web.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * 基本Servlet，后期实现功能的Servlet都要去继承该Servlet
 * @author Administrator
 *
 */
public class BaseServlet extends HttpServlet {
	//项目的根路径
	protected String contextPath = null;
	/***
	 * 核心处理方法,该方法根据用户不同的请求，将处理交给具体的servlet
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contextPath = request.getContextPath();
		//request.setCharacterEncoding("UTF-8");
		//获得用户提交的参数,该参数的值必须为一个已存在的Servlet方法的方法名
		String operate = request.getParameter("operate");
		//通过反射获得该方法名对应的方法对象
		try {
			Method method = this.getClass().getDeclaredMethod(operate, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
