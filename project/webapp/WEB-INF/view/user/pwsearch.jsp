<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="../styles/layout.css">
<!-- <link rel="stylesheet" href="../styles/layout.css"> -->
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid ;
  text-align: left;
  padding: 8px;
}

/* tr:nth-child(even) { */
/*   background-color: #e6ffbb; */
/* } */

.w3-sidebar a {font-family: "Roboto", sans-serif}
body,h1,h2,h3,h4,h5,h6,.w3-wide {font-family: "Montserrat", sans-serif;}
</style>
</head>
<body>
<div class="w3-container w3-card  w3-round w3-margin" style="text-align:right;background-color: #ffebeb">
  	    <br><br>
	<spring:hasBindErrors name="user">
	<font color="red">
		<c:forEach items="${errors.globalErrors }" var="error">
			<spring:message code="${error.code }"></spring:message>
		</c:forEach>
		</font>
	</spring:hasBindErrors>
<form:form modelAttribute="user"  action="pwsearch.shop" method="post">
<table><caption><strong>비밀번호찾기</strong></caption>
<tr><th>네이버 아이디</th><td><input type="text" name="naverid"></td></tr>
<tr><th>네이버 비밀번호</th><td><input type="password" name="naverpw"></td></tr>
<tr><th>아이디</th><td><input type="text" name="id">
<font color="red"><form:errors path="id" /></font></td></tr>
<tr><th>이메일</th><td><input type="text" name="email">
<font color="red"><form:errors path="email" /></font></td></tr>
<tr><th>전화번호</th><td><input type="text" name ="tel">
<font color="red"><form:errors path="tel" /></font></td></tr>
<tr><td colspan="2"><input type="submit" value="비밀번호찾기"></td></tr>
</table></form:form>
</div>
</body>
</html>