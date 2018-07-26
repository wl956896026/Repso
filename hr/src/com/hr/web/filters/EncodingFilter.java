package com.hr.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * ���ñ��뼯�Ĺ�����
 * @author Administrator
 *
 */
@WebFilter(filterName="encodingFilter",urlPatterns="/*")
public class EncodingFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	/***
	 * ���˷������������Ĵ���д�����У�ϵͳ���Զ�ִ��
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("ִ�б��������...");
		//��ServletRequest����ת��ΪHttpServletRequest
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		request.setCharacterEncoding("UTF-8");
		//������Ӧ����
		response.setContentType("text/html;charset=utf-8");
		//����ǰ�������Ӧ������һ����������web��Դ(JSP|HTML|Servlet)
		chain.doFilter(request, response);//�ָ��ߣ�֮ǰ�Ĵ�����������ִ�У�֮��Ĵ�������Ӧ��ִ��
		System.out.println("��ʼ��Ӧ....");
	

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
