package com.hr.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HideServlet extends HttpServlet {
@Override
protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	//�����ǰ������ֱ�ӻ�ȡ�������ǰ�������򴴽�һ����session
	HttpSession session= request.getSession();
	//�������Ϊtrue���request.getSession()һ�����������Ϊfalse���ʾ�������ǰ����sessionֱ�ӻ�ȡ���������򷵻�null
	HttpSession session1 = request.getSession(false|true);
	String username = request.getParameter("username");
	String id = request.getParameter("id");
	System.out.println(username+"   "+id  );
}
}
