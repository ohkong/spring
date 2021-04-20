<%@ page language="java" contentType="text/html; charsetUTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
</script>
<form name='f'>
	<input type="radio" name="v" value="S&P">S&P
	<input type="radio" name="v" value="volume">volume
	<select onchange="javascript:submit()" name="value">
		<option value="선택">선택</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
	</select>
</form>
${param.value }
<table>

<tr><th>다우 존스 산업평균지수 종목</th><th align="center">s&p 500</th></tr>
<tr><td><a href="list2.shop">전체</a></td>
<td><a href="list.shop">전체</a></td></tr>
<c:forEach items="${list.keySet() }" var="str">
	<tr><td><a href="list2.shop?classi=${str}">${list[str] }</a></td>
	<td><a href="list.shop?classi=${str}">${list[str] }</a></td>
	</tr>
</c:forEach>
</table>
</body>
</html>