<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
<title>${title }찾기</title>
</head>
<body>
<div class="w3-container w3-card  w3-round w3-margin" style="text-align:right;background-color: #ffebeb">
  	    <br><br>
<table>
<tr><th>${title }찾기</th><td>${res }</td></tr>
<tr><td colspan="2">
	<c:if test="${title=='아이디' }">
		<input type="button" value="아이디전송" onclick="sendclose()">
	</c:if>
	<c:if test="${title !='아이디' }">
		<input type="button" value="닫기" onclick="self.close()">
	</c:if>
</td></tr>
</table>

</div>
<script type="text/javascript">
function sendclose(){
	opener.$('input#loginid').val('${res}')
	self.close();
}
</script>
</body>
</html>