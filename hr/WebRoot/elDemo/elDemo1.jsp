<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'elDemo1.jsp' starting page</title>
    
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
	${"你好EL" }<br/>
	${1+11 }<br/>
	${100>80?"对了":"错了" }<br/>
	<%
		request.setAttribute("name", "张三");
		session.setAttribute("name", "小李子");
		
		
		String name = (String)request.getAttribute("name");
	 %>
	 <%=name %><br/>
	 ${name }
	 <a href="elDemo2Servlet">ElDemo2</a>
	 <a href="elDemo3Servlet">ELDemo3</a>
  </body>
</html>
