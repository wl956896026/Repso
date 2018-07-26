<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'jstlDemo1.jsp' starting page</title>
    
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
  	set标签的使用:
  	默认存入到pageContext中
  	<c:set var="aa" scope="request" value="<h1 style='color:red'>你好</h1>"></c:set>
  	remove标签:
  	<c:remove var="aa" scope="request"/>
  	out标签的使用:
  	<c:out value="你好"></c:out>
  	
  	<c:out value="${requestScope.xxx }" default="没有数据" ></c:out>
  	<c:out value="${aa}" default="没有数据" escapeXml="false"></c:out>
  	${requestScope.aa}
  	if标签的使用:
  	<c:if test="${1>1 }">
  		真聪明
  	</c:if>
  	choose标签的使用(可以替换if-else标签):
  	<c:choose>
  		<c:when test="${10<20 }">10>20</c:when>
  		<c:when test="${20<10 }">20<10</c:when>
  		<c:otherwise>其他</c:otherwise>
  	</c:choose>
  	<hr/>
  	迭代标签(foreach):
  	<%
  		int[] nums = {12,4,6,8,3,2};
  		request.setAttribute("nums", nums);
  		List<String> strs = new ArrayList<String>();
  		strs.add("aaa");
  		strs.add("bbb");
  		strs.add("ccc");
  		strs.add("ddd");
  		request.setAttribute("strs", strs);
  		
  		
  		
  		Map<String,String> map = new HashMap<String,String>();
  		map.put("key1", "value1");
  		map.put("key2", "value2");
  		map.put("key3", "value3");
  		map.put("key4", "value4");
  		request.setAttribute("map", map);
  		
  	 %>
  	<c:forEach items="${requestScope.nums }" var="num" varStatus="status">
  		${num }==>${status.index }===>${status.count }<br/>
  	</c:forEach>
  	
  	<hr/>
  	<c:forEach items="${requestScope.strs }" var="str">
  		${str }
  	</c:forEach>
  	<hr/>
  	<c:forEach items="${requestScope.map }" var="entry">
  		${entry.key }===>${entry.value }
  	</c:forEach>
  	
  	
  	
  	
  	
  	<c:forEach begin="0" end="10" step="2">
  		a
  	</c:forEach>
  	
  	
  	for(int i=0;i<10;i?++){}
  	
  	
  	
  	
  	
  	
  	
  </body>
</html>
