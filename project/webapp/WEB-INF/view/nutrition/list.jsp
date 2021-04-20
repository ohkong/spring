<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건강정보</title>
</head>
<body>
<table style="width:100&">
<tr><th>이름</th><th>회사이름</th><th>칼로리</th><th>탄수화물</th><th>단백질</th><th>지방</th></tr>
<c:forEach items="${list }" var="str">
	<tr>
	<td>${str.name }</td>
	<td>${str.animal_plant }</td>
	<td>${str.calorie }</td>
	<td>${str.carbohyd }</td>
	<td>${str.protein }</td>
	<td>${str.fat }</td>
	</tr>
</c:forEach>
</table>
</body>
</html>