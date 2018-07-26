package com.hr.web.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * ����Servlet������ʵ�ֹ��ܵ�Servlet��Ҫȥ�̳и�Servlet
 * @author Administrator
 *
 */
public class BaseServlet extends HttpServlet {
	//��Ŀ�ĸ�·��
	protected String contextPath = null;
	/***
	 * ���Ĵ�����,�÷��������û���ͬ�����󣬽������������servlet
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contextPath = request.getContextPath();
		//request.setCharacterEncoding("UTF-8");
		//����û��ύ�Ĳ���,�ò�����ֵ����Ϊһ���Ѵ��ڵ�Servlet�����ķ�����
		String operate = request.getParameter("operate");
		//ͨ�������ø÷�������Ӧ�ķ�������
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
