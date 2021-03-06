<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /webaapp/WEB-INF/view/cart/cart.jsp --%>
<%@ include file="/WEB-INF/view/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
</head>
<body>
<h2>장바구니</h2>
<table>
	<tr><td colspan="4"> 장바구니 상품 목록</td></tr>
	<tr><th>상품명</th><th>가격</th><th>수량</th><th>합계</th>
	<c:set var="tot" value="${0}"/>
	<c:forEach items="${cart.itemSetList}" var="itemSet" varStatus="stat">
		<tr><td>${itemSet.item.name}</td><td>${itemSet.item.price}</td>
		<td>${itemSet.quantity}</td>
		<td>${itemSet.quantity * itemSet.item.price}
			<c:set var="tot"
				value="${tot + (itemSet.quantity * itemSet.item.price) }"/>
		<%-- index 파라미터 : itemSetList의 index 값.
						   itemSetList목록에 index의 객체 삭제. (List.remove()) 
			view : cart/cart
		--%>
			<a href="cartDelete.shop?index=${stat.index}">ⓧ</a></td></tr>
	</c:forEach>
	<tr><td colspan="4" align="right">
		총 구입 금액 : ${tot}원</td></tr>
</table>
<br>
${message}
<br>
<a href="../item/list.shop">상품목록</a>
<a href="checkout.shop">주문하기</a>
</body>
</html>