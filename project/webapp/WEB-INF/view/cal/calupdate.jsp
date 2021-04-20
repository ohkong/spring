<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
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
<meta charset="UTF-8">
<title>가계부 수정</title>
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	function sendclose(){
		window.opener.name = "parentPage";
		$("#up").attr("target", 'parentPage');
		$("#up").attr('action','calupdate.shop');
		$("#up").submit();
		window.open("about:blank","_self").close();
	}
</script>

<link rel="stylesheet" href="../styles/layout.css">
</head>
<body>
<div class="w3-container w3-card  w3-round w3-margin" style="text-align:right;background-color: #ffebeb">
  	    <br><br>
<form id="up" action="calupdate.shop" name="f" method="post">
	<input type="hidden" name="id" value="${loginUser.id}">
	<input type="hidden" name="seq" value="${cal.seq}">
		<table class="w3-table-all">
			<caption>수입/지출 작성</caption>
			<tr><th>날짜</th>
				<td><input type="date" min="2000-01-01" max="2099-12-31" name="date" value="${cal.date}"></td></tr>
			<tr><th>수입/지출 구분</th>
				<td><select name="iotype" class="custom-select custom-select-sm form-control form-control-sm">
						<option value="${cal.iotype}">${cal.iotype==1?"수입":"지출"}(변경시 선택)</option>
						<option value="1">수입</option>
						<option value="2">지출</option></select></td></tr>
			<tr><th>분류</th>
					<td><select name="kind" class="custom-select custom-select-sm form-control form-control-sm">
							<option value="${cal.kind}">${cal.kind}(변경시 선택)</option>
							<optgroup label="수입">
								<option value="용돈">용돈</option>
								<option value="월급">월급</option>
								<option value="기타">기타</option>
							</optgroup>
							<optgroup label=""></optgroup>
							<optgroup label="지출">
								<option value="식비">식비</option>
								<option value="교통">교통</option>
								<option value="문화">문화</option>
								<option value="오락">오락</option>
								<option value="여행">여행</option>
								<option value="패션">패션</option>
								<option value="통신">통신</option>
								<option value="생필품">생필품</option>
								<option value="주거비">주거비</option>
								<option value="공과금">공과금</option>
								<option value="카페">카페</option>
								<option value="술">술</option>
								<option value="취미">취미</option>
								<option value="보험">보험</option>
								<option value="건강관리">건강관리</option>
								<option value="기타">기타</option>
							</optgroup></select></td></tr>
			<tr><th>상세 내역</th>
				<td><input type="text" name="detail" value="${cal.detail}" class="form-control"></td></tr>
			<tr><th>금액</th>
				<td><input type="text" name="price" value="${cal.price}" class="form-control"></td></tr>
		</table>
		<input type="button" value="수정 완료" onclick="sendclose()" class="small">
<!-- 			<input type="submit" value="수정 완료"> -->
			<input type="button" value="취소" onclick="self.close()" class="small">
		</form>
</div>
</body>
</html>