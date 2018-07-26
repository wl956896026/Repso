package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * �˳�Servlet
 * @author Administrator
 *
 */
public class ExitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648351861339107437L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����session��Ҫ��֤session��ֻ�洢��¼�û���������ݣ���û����������
		//request.getSession().invalidate();
		//��ʽ2����session���û���������Ƴ�
		request.getSession().removeAttribute("emp");
		request.getSession().removeAttribute("position");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length!=0){
			for(Cookie cookie :cookies){
				//����¼���Ƿ����cookie��
				if(cookie.getName().equals("loginName")){
					cookie.setMaxAge(0);//����Ϊ0ֱ�Ӵ�cookie���Ƴ�������Ϊ-1�������������
					response.addCookie(cookie);
				}else if(cookie.getName().equals("loginPassword")){//����¼�����Ƿ����cookie��
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		response.sendRedirect("index.jsp");
	}
}
