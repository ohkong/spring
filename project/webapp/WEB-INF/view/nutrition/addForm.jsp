<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="../styles/layout.css">
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
<h1>음식 검색하기</h1>
<form method="post">
<input type="text" name="input">
<input type="submit" value="검색">
<c:if test="${!empty param.input }">
<table>
<tr><th>음식명</th><th>선택하기</th>
<c:forEach items="${list}" var="item">
	<tr>
	<td>${item['name'] }</td><td><input type="button" onclick="javascript:send('${item['name']}','${item['no'] }')" value="선택"> </td>
	</tr>
</c:forEach>
<td></td>
</table>
</c:if>
</form>
<script type="text/javascript">
function send(name,index){
	console.log(name)
	console.log(index)
	opener.document.fo.fod.value=name
	opener.document.fo.fodidx.value=index
	self.close()
}

</script>
</div>
</body>
</html>