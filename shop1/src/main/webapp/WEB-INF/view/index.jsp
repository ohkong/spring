<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--/webapp/WEB-INF/view/index.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>��ǰ ��� ����</title>
</head>
<body>
<h2>��ǰ ���</h2>
<table border="1" style="border-collapse:collapse;">
	<tr><th width="10%">��ǰID</th><th width="50%">��ǰ��</th>
		<th width="20%">����</th><th width="20%">��ǰ�̹���</th></tr>
		<c:forEach items="${itemList}" var="item">
			<tr><td>${item.id}</td>
				<td><a href="detail.shop?id=${item.id}">${item.name}</a></td>
				<td>${item.price}</td>
				<td><img  src="img/${item.pictureUrl}" width="50"/></td></tr>
				</c:forEach>
				</table>
</body>
</html>