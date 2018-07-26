<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<h1 align="center">员工登录</h1>
  
  	 <c:if test="${requestScope.falSEMSG!=null }">
  	 	<span style="color:red">${requestScope.falSEMSG }</span>	
  	 </c:if>
  	
  	<form action="account.do?operate=login" name="loginForm" method="post" >	  		
	  	<table align="center" cellpadding="0" cellspacing="0" border="0">
	 		<tr>
	 			<td>登录名:</td>
	 			<td><input type="text" name="loginName"/></td>
	 		</tr>
	 		<tr>
	 			<td>密码:</td>
	 			<td><input type="password" name="loginPassword"/></td>
	 		</tr>
	 		
	 		<tr>
	 			
	 			<td colspan="2"><input type="checkbox" name="day" value="7"/>7天自动登录</td>
	 		</tr>
	 		<tr>
	 			<td><input type="submit" value="登      录"/></td>
	 			<td><input type="reset" value="重      置"/></td>
	 		</tr>
	  	</table>
  	
  	</form>
  </body>
</html>
