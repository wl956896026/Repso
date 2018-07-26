<%@page import="com.hr.entity.Position"%>
<%@page import="com.hr.entity.Emp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>My JSP 'manager.jsp' starting page</title>

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
	width: 1000px;
	text-align: center;
}

td,th {
	border: 1px solid black;
	height: 40px;
}

th {
	background-color: gray;
	color: white;
	font-weight:bold;
	height: 40px;
}
</style>
<script type="text/javascript">
	function search() {
		var empName = document.getElementById("search").value;
		location.href = "${pageContext.servletContext.contextPath}/emp/emp.do?operate=queryEmp&empName="
				+ empName;
	}
	/**
	搜索框自动补全功能
	 */
	var empName = "";//判断输入值是否一致的全局变量
	function queryEmpName() {
		//获得输入框的值
		var nameStr = document.getElementById("search").value;
		//获得显示员工姓名的div
		var searchDiv = document.getElementById("searchName");
		searchDiv.innerHTML="";//清除DIV中原有的内容
		if(nameStr==""){
			empName="";
			return;
		}
		if (nameStr != "") {
			//判断获取的数据是否与原有的数据一致，不一致才执行功能
			if (nameStr != empName) {
				empName = nameStr;
				//1.创建XMLHttpRequest对象
				//2.调用xmlHttpRequest中的open方法建立与服务器的连接
				//3.注册回调函数，等待获取服务端回传的数据
				//4.发送请求
				//5.在回调函数中处理服务端回传的数据

				//获得XMLHttpRequest对象
				var xmlHttpRequest = new XMLHttpRequest();
				//建立与服务端的连接
				xmlHttpRequest
						.open(
								"GET",
								"${pageContext.servletContext.contextPath}/emp/emp.do?operate=queryEmpName&empName="
										+ nameStr);
				//注册回调函数(当状态码改变时自动执行onreadystatechange事件对应的函数)
				xmlHttpRequest.onreadystatechange = function() {
					//判断请求及响应是否成功
					if (xmlHttpRequest.readyState == 4
							&& xmlHttpRequest.status == 200) {
						//获得服务端回传的json数组,获得的是一个JSON字符串通过eval函数将字符串转换为JSON数组
						var empNameJSONArr = eval(xmlHttpRequest.responseText);
						searchDiv.style.display="block";
						for(var i=0;i<empNameJSONArr.length;i++){
							 	searchDiv.innerHTML=searchDiv.innerHTML+"<span id='searchDiv"+i+"' style='margin:0;height:30px;border:0px solid red;display:block;line-height:30px;padding-left:20px;font-size:14px;' onmouseover='searchOver(\"searchDiv"+i+"\")' onmouseout='searchOut(\"searchDiv"+i+"\")' onclick='searchClick(\"searchDiv"+i+"\")'>"+empNameJSONArr[i]+"</span>";
							 	/* if(i!=empNameJSONArr.length-1){
							 		searchDiv.innerHTML=searchDiv.innerHTML+"<br/>";
							 	} */
						} 
					}
				}

				//发送请求
				xmlHttpRequest.send(null);
			}
		}
	}
	
	/*搜索div中每个span的鼠标悬浮事件*/
	function searchOver(id){
		var spanObj = document.getElementById(id);
		spanObj.style.cursor="hand";
		spanObj.style.backgroundColor="gray";
		spanObj.style.fontWeight="bold";
		spanObj.style.color="white";
	}
	/*搜索div中每个span的鼠标离开事件*/
	function searchOut(id){
		var spanObj = document.getElementById(id);
		spanObj.style.backgroundColor="white";
		spanObj.style.fontWeight="normal";
		spanObj.style.color="black";
	}
	/*单击搜索框div中的span事件*/
	function searchClick(id){
		//获得单击的span中的文本内容
		var spanTxt = document.getElementById(id).innerHTML;
		document.getElementById("search").value=spanTxt;
		document.getElementById("searchName").style.display="none";
		empName=document.getElementById("search").value;
	}
</script>
</head>

<body>
	<%@include file="top.jsp"%>
	<h1 align="center">
		员工列表<span style="position:absolute;right:100px"> <input
			type="text" name="search" id="search" onkeyup="queryEmpName()" /><input
			type="button" value="搜   索" onclick="search()" /> </span>
			
	</h1>
<div id="searchName" style="position:absolute;display:none;border:1px solid black;width:204px;background-color:white;right:101px;top:92px;"></div>
	<a href="${pageContext.servletContext.contextPath }/emp/addEmp.jsp">添加员工</a>
	<c:choose>
		<c:when
			test="${requestScope.emps==null || fn:length(requestScope.emps)==0}">
			<h1 align="center">您查看的员工不存在!</h1>
		</c:when>
		<c:otherwise>
			<table border="0" cellpadding="0" cellspacing="0" align="center">
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>出生日期</th>
					<th>联系电话</th>
					<th>入职时间</th>
					<th>职位</th>
					<th>部门</th>
					<th>账户状态</th>
					<th colspan="3">操作</th>
				</tr>
				<c:forEach items="${requestScope.emps }" var="empArr"
					varStatus="status">

					<tr>
						<td>${status.count }</td>
						<td>${empArr[1]}</td>
						<td>${empArr[2]?"男":"女"}</td>
						<td>${empArr[3]}</td>
						<td>${empArr[4]}</td>
						<td>${empArr[5]}</td>
						<td>${empArr[6]}</td>
						<td>${empArr[7]}</td>
						<td>${empArr[8]==0?"正常":"冻结"}</td>
						<td><a
							href="${pageContext.servletContext.contextPath }/emp/emp.do?operate=empDesc&emp_id=${empArr[0]}">查看详情</a>
						</td>
						<td><a href="">${empArr[8]==0?"冻 结":"解 冻"}</a></td>
						<td><a href="">离 职</a></td>
					</tr>

				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>



</body>
</html>
