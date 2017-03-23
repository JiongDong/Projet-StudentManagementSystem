<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>学生列表</title>
    
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
<h3 align="center">学生列表</h3>
<table border="1" width="70%" align="center">
	<tr>
		<th>学生姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>手机</th>
		<th>邮箱</th>
		<th>描述</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${requestScope.pb.beanList }" var="stu">
		<tr>
			<td>${stu.sname }</td>
			<td>${stu.gender }</td>
			<td>${stu.birthday }</td>
			<td>${stu.tellphone }</td>
			<td>${stu.email }</td>
			<td>${stu.description }</td>
			<td>
				<a href="<c:url value='/StudentServlet?method=preEdit&sid=${stu.sid }'/>">编辑</a>
				<a href="<c:url value='/StudentServlet?method=delete&sid=${stu.sid }'/>">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
<br/>
<center>
第${pb.pageCode }页/共${pb.totalPage }页
<a href="${pb.url }&pc=1"> 首页</a>
<c:if test="${pb.pageCode > 1 }">
<a href="${pb.url }&pc=${pb.pageCode-1 }">上一页</a>
</c:if>
<!-- 页码表  计算begin end-->
<c:choose>
	<c:when test="${pb.totalPage<=10 }">
		<c:set var="begin" value="1"></c:set>
		<c:set var="end" value="${pb.totalPage }"></c:set>
	</c:when>
	<c:otherwise>
		<%-- 计算公式 --%>
		<c:set var="begin" value="${pb.pageCode-5 }"></c:set>
		<c:set var="end" value="${pb.pageCode+4 }"></c:set>
		<%--- 头溢出 --%>
		<c:if test="${begin<1 }">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="10"></c:set>
		</c:if>
		<%-- 尾溢出 --%>
		<c:if test="${end> pb.totalPage}">
			<c:set var="begin" value="${pb.totalPage-9 }"></c:set>
			<c:set var="end" value="${pb.totalPage }"></c:set>
		</c:if>
	</c:otherwise>
</c:choose>
<%-- 循环显示页码列表 --%>
<c:forEach var="i" begin="${begin }" end="${end }">
	<c:choose>
		<c:when test="${i eq pb.pageCode }">
			[${i }]
		</c:when>
		<c:otherwise>
			<a href="${pb.url }&pc=${i }">[${i }]</a>
		</c:otherwise>
	</c:choose>	
</c:forEach>

<c:if test="${pb.pageCode < pb.totalPage }">
<a href="${pb.url }&pc=${pb.pageCode+1 }">下一页</a>
</c:if>
<a href="${pb.url }&pc=${pb.totalPage }">尾页</a>
</center>
  </body>
</html>
