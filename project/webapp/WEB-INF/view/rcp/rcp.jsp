<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>레시피 보기</title>
</head>
<body>
<table>
<tr><th colspan="3" align="center"  width="70%"><h1 style="font-size : 40px">${menu}</h1></th></tr>
<tr>
<td rowspan="4" width="250px" height="250px"><img src="${rcp.food_pic }"  ></td></tr>
<tr><th width="100px">재료</th><td> ${rcp.raw}</td></tr>
<tr><th width="100px">조리방법</th><td> ${rcp.recway }</td></tr>
<tr><th width="100px">타입</th><td> ${rcp.recpat }</td></tr>
<c:forEach begin = "0" end="${length}" var="i">
<tr>
	<td>${rec[i] }</td>
	<td colspan="2"><img src="${recp[i] }"></img></td>
</tr>
</c:forEach>
<tr><td colspan="3"><a href="menu.shop">목록</a></td></tr>
</table>
</body>
</html>