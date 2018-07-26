<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<c:if test="${sessionScope.emp!=null }">
  		<h3 align="right">
  				欢迎您:${sessionScope.emp.emp_name }&nbsp;&nbsp;<span style="color:blue">${sessionScope.position.position_name}</span>
  				
  				<a href="${pageContext.servletContext.contextPath }/account.do?operate=exit">退  出</a>
  			</h3>
  	</c:if>
  
  	 <hr/>