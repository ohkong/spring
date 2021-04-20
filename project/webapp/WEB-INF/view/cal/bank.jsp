<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목돈 마련하자</title>
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#bkinfo").show();
	$("#sfinfo").hide();
	$("#cuinfo").hide();
	$(".dayLine").each(function(){
		$(this).hide();
	})
	$("#tab1").addClass("select");
})
function disp_div(id,tab){
		$(".info").each(function(){
			$(this).hide();
		})
		$(".tab").each(function(){
			$(this).removeClass("select");
		})
		$("#"+id).show();
		$("#" + tab).addClass("select");
	}
</script>
<style type="text/css">
	a{
		color : #ffffff;
	}
	a:hover{
		color : #c58f8f;
	}
	.select{
		padding : 3px;
		background-color:#c58f8f;
	}
	.select>a{
		color : #ffffff;
		text-decoration: none;
		font-weight: bold;
	}
</style>
</head>
<body>
<table>
	<tr><th id="tab1" class="tab" style="text-align:center; width:33%;" ><a href="javascript:disp_div('bkinfo','tab1')">은행</a></th>
		<th id="tab2" class="tab" style="text-align:center; width:33%;"><a href="javascript:disp_div('sfinfo','tab2')">상호저축은행</a></th>
		<th id="tab3" class="tab" style="text-align:center; width:34%;"><a href="javascript:disp_div('cuinfo','tab3')">신협</a></th></tr>
</table>
<div style="text-align: right;"><font style='font-weight: bold;'>12개월 10만원씩 입금 기준</font></div>
<div id="bkinfo" class="info" style="display:none; width:100%;">
<div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  	<div class="w3-container w3 -padding-16">
  		<div id ="mone1" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  		</div>
  	</div>
 </div>
</div>
 
<div id="sfinfo" class="info" style="display:none; width:100%;">
 <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  	<div class="w3-container w3 -padding-16">
  		<div id ="mone2" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  		</div>
  	</div>
 </div>
</div>
 
<div id="cuinfo" class="info" style="display:none; width:100%;">
 <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  	<div class="w3-container w3 -padding-16">
  		<div id ="mone3" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  		</div>
  	</div>
 </div>
</div>

<script> 
	$.ajax({
		url:"${path}/ajax/mone1.shop",
		success : function(data){
		console.log(data);
			$("#mone1").html(data);
		},
		error : function(e){
			alert("은행1 정보 오류:"+e.status);
		}
	})
	$.ajax({
		url:"${path}/ajax/mone2.shop",
		success : function(data){
		console.log(data);
			$("#mone2").html(data);
		},
		error : function(e){
			alert("은행2 정보 오류:"+e.status);
		}
	})
	$.ajax({
		url:"${path}/ajax/mone3.shop",
		success : function(data){
		console.log(data);
			$("#mone3").html(data);
		},
		error : function(e){
			alert("은행3 정보 오류:"+e.status);
		}
	})
	
</script>
</body>
</html>