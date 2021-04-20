<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /webaapp/WEB-INF/view/user/userEntry.jsp 
	http://localhost:8080/shop3/user/userEntry.shop 요청시 화면 출력하기--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 등록</title>
</head>
<body>
<h2>사용자 등록</h2>
<form:form modelAttribute="user" method="post" action="userEntry.shop" name="f" onsubmit="return submittest()">
	<input type="hidden" name="checkemail" id="checkemail"   value="${param.checkemail}">
	<spring:hasBindErrors name="user">
		<font color="red">
			<c:forEach items="${errors.globalErrors}" var="error">
				<spring:message code="${error.code}"/>
			</c:forEach>
		</font>
	</spring:hasBindErrors>
	<table border="1" style="border-collapse:collapse;">
		<tr height="40px"><td><font color="red">*</font> 아이디</td><td><form:input path="id" id="id"/>
			<font color="red"><form:errors path="id"/></font></td></tr>
		<tr height="40px"><td><font color="red">*</font> 비밀번호</td><td><form:password path="pass" id="pass"/>
			<font color="red"><form:errors path="pass"/></font></td></tr>
		<tr height="40px"><td><font color="red">*</font> 이름</td><td><form:input path="name" id="name"/>
			<font color="red"><form:errors path="name"/></font></td></tr>
    	<tr height="40px"><td>전화번호</td><td><form:input path="tel" id="tel"/>
			</td></tr>						  
		<tr height="40px"><td>주소</td><td><form:input path="address" id="address"/>
			<font color="red"><form:errors path="address"/></font></td></tr>
		<tr height="40px"><td><font color="red">*</font> 이메일</td><td><form:input path="email" id="email"  onclick="win_open('emailcheck')" readonly="true" placeholder="여기를 클릭하세요 "/>
			<font color="red"><form:errors path="email"/></font>
			<c:if test="${!empty param.checkemail }"><font id="mailok"  color="blue">인증성공</font></c:if>
			</td></tr>
		<tr height="40px"><td><font color="red">*</font> 생년월일</td><td>
		<ul class="resultSet" style="vertical-align: middle">
		<li class="item"><form:input style="width: 100%" path="birthday" id="birthday"   size="6" placeholder="●●●●●●" onkeyup="numCheck()"/></li>
		<li class="item" style="vertical-align: middle;font-size: 20px">&nbsp;_&nbsp;</li>
		<li class="item"><form:input style="width: 100%"  path="gender"  id="gender" size="6" placeholder="●******" onkeyup="numCheck()" /></li>
		<li class="item">	<font color="red"><form:errors path="birthday"/> <form:errors path="gender"/></font></li>
		</ul>	
			</td>
			</tr>
		<tr height="40px"><td><font color="red">*</font> 통장 잔액</td><td><form:input path="sum" id="sum"   onkeyup="numCheck()"/>
			<font color="red"><form:errors path="sum"   /></font></td></tr>
		<tr height="40px"><td colspan="2" align="center">
			<input type="submit" value="등록">
			<input type="reset" value="초기화"></td></tr>	
	</table>
</form:form>
<script type="text/javascript">
$(function(){
	var gender = $( 'input#gender' ).val();
	var tel = $( 'input#tel' ).val();
	var sum = $( 'input#sum' ).val();
	if(gender==0)
		$( 'input#gender' ).val("")
	if(tel==0)
		 $( 'input#tel' ).val("");
	if(sum==0)
		$( 'input#sum' ).val("");
})

function submittest(){
	if($( 'input#checkemail' ).val()==""){
		alert("이메일 인증해주세요. ")
		return false;
	}
	return true;
}

	   function win_open(page) {
		   var op = "width=500, height=350, left=50,top=150";
		   open(page+".shop","",op);
	   }
function numCheck(){
	
	
	if($( 'input#gender' ).val().length>1 || isNaN($( 'input#gender' ).val())){
		$( 'input#gender' ).val($( 'input#gender' ).val().substring(0,$( 'input#gender' ).val().length-1))
	}
	if($( 'input#birthday' ).val().length>6 || isNaN($( 'input#birthday' ).val())){
		$( 'input#birthday' ).val($( 'input#birthday' ).val().substring(0,$( 'input#birthday' ).val().length-1))
	}
	if(isNaN($( 'input#sum' ).val())){
		$( 'input#sum' ).val($( 'input#sum' ).val().substring(0,$( 'input#sum' ).val().length-1))
	}
// 	if(f.birthday.value.length>6){
// 		f.birthday.value= f.birthday.value.substring(0,f.birthday.value.length-1)
// 	}
// 	if(isNaN(f.birthday.value)){
// 		f.birthday.value= f.birthday.value.substring(0,f.birthday.value.length-1)
// 	}
// 	if(isNaN(f.sum.value)){
// 		f.sum.value= f.sum.value.substring(0,f.sum.value.length-1)
// 	}
}
</script>
</body>
</html>