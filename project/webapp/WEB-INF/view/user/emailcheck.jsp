<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
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
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div class="w3-container w3-card  w3-round w3-margin" style="text-align:right;background-color: #ffebeb">
  	    <br><br>
이메일 체크하기

<form name ="f" method="post" >
<table>
<tr>
<th>이메일 </th> <td><input name="email" value="${email }"></td>
<td><input type="submit" value="인증번호 전송"></td></tr>
<c:if test='${!empty email }'>
<tr><th>인증번호</th><td><input id="input"></td><td><div id="ViewTimer"></div>
<input type="button" value="인증번호 확인" onclick="codeCheck()"></td>
</tr>
</c:if>
</table>
</form>

<script>
	console.log("${code}")
	window.onload = function TimerStart(){ tid=setInterval('msg_time()',1000) }
		
		
		var SetTime = 30;		// 최초 설정 시간(기본 : 초)
		function codeCheck(){
			if("${code}" ==document.getElementById("input").value){
				alert("인증성공")
				opener.$( 'input#email' ).val("${email}")
				opener.$( 'input#checkemail' ).val(2)
				self.close()
			}else{
				alert("인증실패")
				document.getElementById("input").value=""
			}
		}
		function msg_time() {	// 1초씩 카운트
			m = Math.floor(SetTime / 60) + "분 " + (SetTime % 60) + "초";	// 남은 시간 계산
			var msg = "<font color='red'>"+m +"</font>";
			document.all.ViewTimer.innerHTML = msg;

			SetTime--;					// 1초씩 감소
			if (SetTime < 0) {			// 시간이 종료 되었으면..
				clearInterval(tid);		// 타이머 해제
				alert("다시 시도해주세요.");
				self.close()
				
			}
		}

</script>
</div>
</body>
</html>