<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
  <head>
    
    
    <title>My JSP 'fileUpload.jsp' starting page</title>
    
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
    <form action="${pageContext.servletContext.contextPath }/emp/upload.do" method="post" enctype="multipart/form-data">
    	用户名:<input type="text" name="username"/><br/>
    	照片:<input id="p" type="file" name="photo"/><br/>
    	照片:<input type="file" name="photo1"/><br/>
    	<input type="submit" value="上传"/>
    </form>
  </body>
</html>
