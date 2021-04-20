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
<meta charset="EUC-KR">
<title>사용자 정보 수정</title>
</head>
<body>
<form:form modelAttribute="user" method="post" action="update.shop">
	<spring:hasBindErrors name="user">
		<font color="red">
			<c:forEach items="${errors.globalErrors }" var="error">
				<spring:message code="${error.code }" />
			</c:forEach>
		</font>
	</spring:hasBindErrors>
	<table>
		<tr><td>아이디</td>
		<td><form:input path="id" readonly="true" />
		<font color="red"><form:errors path="id" /></font></td></tr>
	<tr height="40px"><td>비밀번호</td><td><form:password path="pass" />
	<font color="red"><form:errors path="pass" /></font></td></tr>
	<tr height="40px"><td>이름</td><td><form:input path="name" />
	<font color="red"><form:errors path="name" /></font></td></tr>
	<tr height="40px"><td>전화번호</td><td><form:input path="tel" />
	<font color="red"><form:errors path="tel" /></font></td></tr>
	<tr height="40px"><td>주소</td><td><form:input path="address" />
	<font color="red"><form:errors path="address" /></font></td></tr>
	<tr height="40px"><td>이메일</td><td><form:input path="email"  readonly="true"/></td></tr>
		<tr height="40px"><td>생년월일</td><td> 
			<ul class="resultSet">
					<li class="item"><form:input  path="birthday" size="6" placeholder="●●●●●●" onkeyup="numCheck()" readonly="true"/></li>
					<li class="item">-</li>
					<li class="item"><form:input  path="gender"  size="6" placeholder="●******" onkeyup="numCheck()" readonly="true"/></li>
			</ul>
			<font color="red"><form:errors path="birthday"/> <form:errors path="gender"/></font></td></tr>
	<tr height="40px"><td>통장 잔액</td><td><form:input path="sum" />
	<font color="red"><form:errors path="sum" /></font></td></tr>
	<tr height="40px"><td>키</td><td><form:input path="height" />m
	<font color="red"><form:errors path="height" /></font></td></tr>
	<tr height="40px"><td>몸무게</td><td><form:input path="weight" />kg
	<font color="red"><form:errors path="weight" /></font></td></tr>
	<tr height="40px"><td colspan="2" align="center">
	<ul class="resultSet">
		<li class="item"><input type="submit" value="수정"></li>
		<li class="item"><input type="reset" value="초기화"></li>
		</ul>
		<c:if test="${sessionScope.loginUser.id ==param.id }">
<%-- 		<input type="button" value="비밀번호변경(이동)" onclick="location.href='password.shop?id=${param.id}'"> --%>
		</c:if>
	</td></tr>
	</table>
</form:form>
<script type="text/javascript">
   function win_open(page) {
	   var op = "width=500, height=350, left=50,top=150";
	   open(page,"",op);
   }
   </script>
</body>
</html>