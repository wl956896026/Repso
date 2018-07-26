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
	<script type="text/javascript">
		function upload(){
			var xmlHttpRequest = new XMLHttpRequest();
			var path = document.getElementById("p").value;
			var formdata = new FormData();
			//alert(document.getElementById("p").files[0]);
			formdata.append("path",document.getElementById("p").files[0]);
		//	alert(formdata);	
		
			 //建立与服务端的连接
			xmlHttpRequest.open("POST", "${pageContext.servletContext.contextPath}/emp/upload1.do");
			//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
			xmlHttpRequest.onreadystatechange=function(){
				//alert(xmlHttpRequest.readyState);
				//判断请求及响应是否成功
				if(xmlHttpRequest.readyState==4 && xmlHttpRequest.status==200){
					
					var imagePath = xmlHttpRequest.responseText;
					alert(imagePath);
					var imageObj = document.getElementById("image");
					imageObj.src="${pageContext.servletContext.contextPath}/"+imagePath;
					imageObj.style.display="inline";
					
				}
			}
			/* xmlHttpRequest.setRequestHeader("content-Type",
											"application/x-www-form-urlencoded"); */
											xmlHttpRequest.setRequestHeader("enctype", "multipart/form-data");
			//发送请求
			xmlHttpRequest.send(formdata); 
		}
	</script>
  </head>
  
  <body>
    <form action="${pageContext.servletContext.contextPath }/emp/upload.do" method="post" enctype="multipart/form-data">
    	用户名:<input type="text" name="username"/><br/>
    	照片:<input id="p" type="file" name="photo"/><input type="button" value="上传" onclick="upload()"/><img id="image" width="150" style="display:none;" height="150"/><br/>
    	照片:<input type="file" name="photo1"/><br/>
    	<input type="submit" value="上传"/>
    </form>
  </body>
</html>
