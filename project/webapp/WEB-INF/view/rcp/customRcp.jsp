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
<tr><th colspan="3" align="center" width="70%"><h1 style="font-size : 40px">${menu}</h1></th></tr>
<tr>
<td rowspan="5"><img src="file/${rcp.getRecipe_pic()}" width="250px" height="250px" ></td></tr>
<tr><th width="100px">작성자</th><td>${rcp.id }</td></tr>
<tr><th width="100px">재료</th><td> ${rcp.raw}</td></tr>
<tr><th width="100px">조리방법</th><td> ${rcp.recway }</td></tr>
<tr><th width="100px">타입</th><td> ${rcp.recpat }</td></tr>
<c:forEach begin = "0" end="${length}" var="i">
<tr>
	<td>${rec[i] }</td>
	<td colspan="2"><img src="file/${recp[i] }" style="height: 150px"></img></td>
</tr>
</c:forEach>

<tr><td colspan="3">
	<form action="delrcp.shop" method="post" name="delform">
	<input type="hidden" name="no" id="no">
	</form>
<c:if test="${loginUser.id == rcp.id }">
	<a href="update.shop?no=${rcp.no }">수정</a>
	<a href="javascript:del(${rcp.no })">삭제</a>
</c:if>
	<a href="custommenu.shop">목록</a>



</td></tr>



</table>
<script type="text/javascript">
function del(no){
	if(confirm("정말삭제하시겠습니까?")==true){
		$("input#no").val(no)
		console.log($("input#no").val())
		delform.submit()}
	else
		return ;
}
</script>
</body>
</html>