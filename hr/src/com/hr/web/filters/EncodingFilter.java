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
 * 设置编码集的过滤器
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
	 * 过滤方法，过滤器的代码写在其中，系统会自动执行
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("执行编码过滤器...");
		//将ServletRequest对象转换为HttpServletRequest
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		request.setCharacterEncoding("UTF-8");
		//设置响应类型
		response.setContentType("text/html;charset=utf-8");
		//将当前请求或响应交给下一个过滤器或web资源(JSP|HTML|Servlet)
		chain.doFilter(request, response);//分割线，之前的代码在请求期执行，之后的代码在响应期执行
		System.out.println("开始响应....");
	

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
