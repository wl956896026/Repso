<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'deptDesc.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		table{
			width:800px;
			border:1px solid black;
			border-collapse: collapse;
		}
		td{
			border:1px solid black;
			height:50px;
			width:100px;
			font-size:16px;
			text-align:center;
		}
	</style>
  </head>
  
  <body>
  	<h1 align="center">员工档案</h1>
  	<table align="center" cellpadding="0" cellspacing="0" border="0">
  		<tr>
  			<td>姓名</td>
  			<td>${requestScope.empInfo[1]}</td>
  			<td>性别</td>
  			<td>${requestScope.empInfo[3]==0?"女":"男"}</td>
  			<td>出生日期</td>
  			<td>${requestScope.empInfo[4]}</td>
  			<td colspan="2" rowspan="4">
  				<img style="width:150px;height:150px;" src="${pageContext.servletContext.contextPath }/upload/images/${requestScope.empInfo[20]}"/></td>
  		</tr>
  		
  		<tr>
  			<td>学历</td>
  			<td>${requestScope.empInfo[5]}</td>
  			<td>专业</td>
  			<td>${requestScope.empInfo[6]}</td>
  			<td>毕业院校</td>
  			<td>${requestScope.empInfo[7]}</td>
  		</tr>
  		
  		<tr>
  			<td>登录名</td>
  			<td>${requestScope.empInfo[2]}</td>
  			<td>联系电话</td>
  			<td>${requestScope.empInfo[8]}</td>
  			<td>E-mail</td>
  			<td>${requestScope.empInfo[9]}</td>
  		</tr>
  		
  		<tr>
  			<td>部门</td>
  			<td>${requestScope.empInfo[12]}</td>
  			<td>岗位</td>
  			<td>${requestScope.empInfo[11]}</td>
  			<td>入职时间</td>
  			<td>${requestScope.empInfo[10]}</td>
  		</tr>
  		<tr>
  			<td>员工状态</td>
  			<td colspan="3">
  				<c:choose>
  					<c:when test="${requestScope.empInfo[14]==0}">
  						正常
  					</c:when>
  					<c:when test="${requestScope.empInfo[14]==1}">
  						离职
  					</c:when>
  					<c:when test="${requestScope.empInfo[14]==2}">
  						退休
  					</c:when>
  				</c:choose>
  				</td>
  			<td>账户状态</td>
  			<td colspan="3">${requestScope.empInfo[13]==0?"正常":"冻结"}</td>
  		</tr>
  		<tr>
  			<td colspan="8" style="background-color:gray;font-weight: bold;color:white;">工  作  经  验</td>
  		</tr>
  		<tr>
  			<td>曾就职公司</td>
  			<td>${requestScope.empInfo[17]}</td>
  			<td>职位</td>
  			<td>${requestScope.empInfo[18]}</td>
  			<td>入职时间</td>
  			<td>${requestScope.empInfo[15]}</td>
  			<td>离职时间</td>
  			<td>${requestScope.empInfo[16]}</td>
  		</tr>
  		<tr>
  			<td colspan="8">工作内容</td>
  		</tr>
  		<tr>
  			<td colspan="8" style="height:200px;">${requestScope.empInfo[19]}</td>
  		</tr>
  		<tr>
  			<td colspan="8">
  				<a href="${pageContext.servletContext.contextPath }/emp/emp.do?operate=queryEmp">返回</a>&nbsp;&nbsp;
  				<a href="">冻结账户</a>&nbsp;&nbsp;
  				<a href="">离职</a>&nbsp;&nbsp;
  			</td>
  		</tr>
  	</table>
  </body>
</html>
