<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /webapp/WEB-INF/view/user/mypage.jsp --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>
<div>
<table>
	<tr><td>아이디</td><td>${user.id}</td></tr>
	<tr><td>이름</td><td>${user.name}</td></tr>
	<tr><td>성별</td><td>${user.getGender()==1?"남자":"여자"}</td></tr>
	<tr><td>전화번호</td><td>${user.tel}</td></tr>
	<tr><td>생년월일</td><td>${user.birthday}</td></tr>
	<tr><td>주소</td><td>${user.address}</td></tr>
	<tr><td>이메일</td><td>${user.email}</td></tr>
	<tr><td>통장 잔액</td><td>${user.sum}</td></tr>
	<tr><td>키</td><td>${user.height}m</td></tr>
	<tr><td>몸무게</td><td>${user.weight}kg</td></tr>
</table><br>
<a href="update.shop?id=${loginUser.id}">[회원정보수정]</a>&nbsp;
<a href="password.shop">[비밀번호수정]</a>&nbsp;
	<c:if test="${loginUser.id != 'admin'}">
		<a href="delete.shop?id=${loginUser.id}">[회원탈퇴]</a>&nbsp;
	</c:if>
</div><br>
</body>
</html>