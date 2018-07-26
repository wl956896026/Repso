<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html>
<html>
<head>
<title>My JSP 'addEmp.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
table {
	width: 460px;
	border: 1px solid black;
	border-collapse: collapse;
}

td {
	border: 0px solid black;
	padding: 5px;
}
</style>
<script type="text/javascript">
	/**
		使用ajax检测登录名是否可用
	 **/
	function checkLoginName() {
		//获得XMLHttpRequest对象
		var xmlHttpRequest = new XMLHttpRequest();
		var loginName = document.getElementById("login_name").value;
		//建立与服务端的连接
		xmlHttpRequest
				.open("POST",
						"${pageContext.servletContext.contextPath}/emp/emp.do?operate=checkLoginName");
		//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
		xmlHttpRequest.onreadystatechange = function() {
			//alert(xmlHttpRequest.readyState);
			//判断请求及响应是否成功
			if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				//接收服务的回传的数据
				var txt = eval(xmlHttpRequest.responseText);//获得服务端提交的文本
				var msgObj = document.getElementById("msg");
				if (txt) {
					msgObj.style.color = "green";
					msgObj.innerHTML = "该用户名可用";
				} else {
					msgObj.style.color = "red";
					msgObj.innerHTML = "该用户名已被占用";
				}
				/* if(txt=="true"){
					msgObj.style.color="green";
					msgObj.innerHTML = "该用户名可用";
				}else{
					msgObj.style.color="red";
					msgObj.innerHTML = "该用户名已被占用";
				} */
			}
		}
		xmlHttpRequest.setRequestHeader("content-Type",
				"application/x-www-form-urlencoded");
		//发送请求
		xmlHttpRequest.send("loginName=" + loginName);
	}
	/**获得所有部门*/

	function queryDept() {
		//获得XMLHttpRequest对象
		var xmlHttpRequest = new XMLHttpRequest();
		//建立与服务端的连接
		xmlHttpRequest
				.open("GET",
						"${pageContext.servletContext.contextPath}/dept/dept.do?operate=queryDept");
		//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
		xmlHttpRequest.onreadystatechange = function() {
			//alert(xmlHttpRequest.readyState);
			//判断请求及响应是否成功
			if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				//通过eval函数将字符串格式的json数据转换为json数组
				var jsonDept = eval(xmlHttpRequest.responseText);
				//获得部门列表对象
				var deptSelectObj = document.getElementById("dept_id");
				for ( var i = 0; i < jsonDept.length; i++) {
					//创建option对象
					//参数1表示option标签的标签体
					//参数2表示option标签的value属性的值
					var deptOption = new Option(jsonDept[i].dept_name,
							jsonDept[i].dept_id);
					//将创建的列表项添加到select对象中
					deptSelectObj.options.add(deptOption);
				}
			}
		}

		//发送请求
		xmlHttpRequest.send(null);
	}
	/**根据部门填充该部门下的职位*/

	function fillPosition() {
		//获得选择的部门编号
		var deptId = document.getElementById("dept_id").value;
		//获得XMLHttpRequest对象
		var xmlHttpRequest = new XMLHttpRequest();
		//建立与服务端的连接
		xmlHttpRequest
				.open(
						"GET",
						"${pageContext.servletContext.contextPath}/position/position.do?operate=queryPositionByDept&deptId="
								+ deptId);
		//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
		xmlHttpRequest.onreadystatechange = function() {
			//alert(xmlHttpRequest.readyState);
			//判断请求及响应是否成功
			if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				var positionObj = document.getElementById("position_id");
				//获得服务端回传的字符串格式的数据并转换JSON数组
				var positionStr = xmlHttpRequest.responseText;
				if (positionStr == "") {
					alert("没有找到该部门的职位");
					positionObj.options.length = 0;
					var otherOption = new Option("---请选择职位---", "");
					positionObj.options.add(otherOption);
					return;
				}
				var positionArr = eval(positionStr);
				//清除列表中的列表项
				positionObj.options.length = 0;
				for ( var i = 0; i < positionArr.length; i++) {
					var positionOption = new Option(
							positionArr[i].position_name,
							positionArr[i].position_id);
					positionObj.options.add(positionOption);
				}
			}
		}

		//发送请求
		xmlHttpRequest.send(null);
	}

	/**使用ajax实现文件上传*/

	function upload() {
		//var filePath = document.getElementById("emp_photo").value;
		//创建一个FormData对象(表单数据对象)
		var formData = new FormData();
		//获得文件对象
		var photoFile = document.getElementById("emp_photo").files[0];
		//向表单数据对象中添加数据
		formData.append("filePath", photoFile);
		//获得XMLHttpRequest对象
		var xmlHttpRequest = new XMLHttpRequest();
		
		//建立与服务端的连接
		xmlHttpRequest
				.open("POST",
						"${pageContext.servletContext.contextPath}/emp/emp.do?operate=photoUpload");
						//设置内容类型为支持多种数据类型的表单数据
		xmlHttpRequest.setRequestHeader("enctype", "multipart/form-data");
		//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
		xmlHttpRequest.onreadystatechange = function() {
			//alert(xmlHttpRequest.readyState);
			//判断请求及响应是否成功
			if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				var jsonArr = eval(xmlHttpRequest.responseText);
				//设置原文件名和新文件名
				document.getElementById("emp_old_photo").value=jsonArr[1];
				document.getElementById("emp_new_photo").value=jsonArr[0];
				var photoObj = document.getElementById("photo");
				photoObj.style.width="150px";
				photoObj.style.height="150px";
				photoObj.src="${pageContext.servletContext.contextPath}/upload/images/"+jsonArr[0];
				photoObj.style.display="block";
			}
		}

		//发送请求,将表单数据对象发送到服务端
		xmlHttpRequest.send(formData);
	}
</script>
</head>

<body onload="queryDept()">
	<h1 align="center">添加员工</h1>
	<form action="emp.do?operate=addEmp" method="post"
		enctype="application/x-www-form-urlencoded">
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>姓名</td>
				<td><input type="text" name="emp_name" />
				</td>
			<tr>
			<tr>
				<td>性别</td>
				<td><input type="radio" name="emp_sex" value="1" />男 <input
					type="radio" name="emp_sex" value="0" />女</td>
			</tr>
			<tr>
				<td>登录名</td>
				<td><input type="text" name="login_name" id="login_name"
					onblur="checkLoginName()" /> <span id="msg"></span></td>
			</tr>
			<tr>
				<td>照片</td>
				<td>
					<input type="file" id="emp_photo" name="emp_photo" />
					<input type="button" value="上传" onclick="upload()">
					<img id="photo"  style="display:none;">
					<input type="hidden" name="emp_old_photo" id="emp_old_photo"/>
					<input type="hidden" name="emp_new_photo" id="emp_new_photo"/>
					</td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td><input type="text" name="emp_birthday" />
				</td>
			</tr>
			<tr>
				<td>学历</td>
				<td><input type="radio" value="专科" name="emp_xueli" />专科 <input
					type="radio" value="本科" name="emp_xueli" />本科 <input type="radio"
					value="硕士" name="emp_xueli" />硕士 <input type="radio" value="博士"
					name="emp_xueli" />博士</td>
			</tr>
			<tr>
				<td>专业</td>
				<td><input type="text" name="emp_major" />
				</td>
			</tr>
			<tr>
				<td>毕业院校</td>
				<td><input type="text" name="emp_school" />
				</td>
			</tr>
			<tr>
				<td>联系电话</td>
				<td><input type="text" name="emp_phone" />
				</td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td><input type="text" name="emp_email" />
				</td>
			</tr>
			<tr>
				<td>入职时间</td>
				<td><input type="text" name="emp_put_time" />
				</td>
			</tr>
			<tr>
				<td>部门</td>
				<td><select name="dept_id" id="dept_id"
					onchange="fillPosition()">
						<option value="">---请选择部门---</option>


				</select></td>
			</tr>
			<tr>
				<td>岗位</td>
				<td><select name="position_id" id="position_id">
						<option>---请选择岗位---</option>

				</select></td>
			</tr>
			<tr>
				<td colspan="2">工作经历</td>
			</tr>
			<tr>
				<td>曾就职公司</td>
				<td><input type="text" name="company" />
				</td>
			</tr>
			<tr>
				<td>岗位</td>
				<td><input type="text" name="position" />
				</td>
			</tr>
			<tr>
				<td>工作时间</td>
				<td><input type="text" name="start_time" />
				</td>
			</tr>
			<tr>
				<td>离职时间</td>
				<td><input type="text" name="end_time" />
				</td>
			</tr>
			<tr>
				<td colspan="2">工作内容</td>
			</tr>
			<tr>
				<td colspan="2"><textarea rows="5" cols="30" name="job_content"></textarea>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="提    交" /></td>
				<td><input type="reset" value="取    消" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
