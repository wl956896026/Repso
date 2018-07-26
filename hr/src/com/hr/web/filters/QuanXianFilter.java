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
import javax.servlet.http.HttpSession;

import com.hr.entity.Emp;
@WebFilter(filterName="aaaa",urlPatterns={/*"/emp/*",*/"/dept/*"})
public class QuanXianFilter implements Filter {
	@Override
	public void destroy() {
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("权限过滤器");
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		//获得session
		HttpSession session = request.getSession();
		Emp emp = (Emp)session.getAttribute("emp");
		if(emp==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		chain.doFilter(request, response);//放行

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
