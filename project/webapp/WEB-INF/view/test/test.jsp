<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<p>아</p>
<div id="juk"></div>
<!-- 
<div id="juk2"></div>
 -->
<script>
$(function(){
	jukRate();	
	//jukRate2();
})
function jukRate(){
	$.ajax("/ojh/ajax/juk.shop",{
		success : function(data){
			//console.log(data);
			$("#juk").html(data);
		},
		error : function(e){
			alert("환율 조회시 서버 오류:"+e.status);
		}
	})
}
function jukRate2(){
	$.ajax("/ojh/ajax/juk2.shop",{
		success : function(data){
			//console.log(data);
			$("#juk2").html(data);
		},
		error : function(e){
			alert(" 서버 오류:"+e.status);
		}
	})
}
</script>
</body>
</html>