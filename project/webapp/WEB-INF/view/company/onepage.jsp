<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title >주식정보 확인하기</title>
<style type="text/css">
</style>
</head>

<body>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<script type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="../js/widget-scroller.js"></script>
	<h1 align="center">오늘 ${param.classi} (${type }) 평균 = ${avg } </h1>
<form name='f' method="post">
	<input type="hidden" name = "order" >
	<ul class="resultSet">
 	<li class="item"><input type="radio" name="v" value="S&P" onclick="javascript:submit()" <c:if test="${param.v!='dow' }"> checked="checked"      </c:if>></li><li class="item"><Strong> S&P</Strong></li>
	<li class="item"><input type="radio" name="v" value="dow" onclick="javascript:submit()" <c:if test="${param.v=='dow' }"> checked="checked"      </c:if>></li><li class="item"><Strong>dow</Strong></li>
	</ul>
	<select onchange="javascript:submit()" name="classi" class="custom-select custom-select-sm form-control form-control-sm">
		<option value="">섹터선택</option>
			<option value="">전체</option>
		<c:forEach items="${cls.keySet() }" var="str">
			<c:if test='${cls[str]!=null &&cls[str]!="" }'>
			<option value="${str}">${cls[str] }</option>
			</c:if>
		</c:forEach>
	</select>
<c:if test="${no==1 }">
	<h1>없습니다.</h1>
</c:if>

<div id="ViewTimer"></div>
<c:if test="${no==2 }">


  <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="piecontainer" style="width:100%;border:1px solid #ffffff">
  			<canvas id = "canvas" style ="width:100%;"></canvas>
  		</div>
  	</div>
  </div>
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="barcontainer" style="width:100%;border:1px solid #ffffff">
  			<canvas id = "canvas2" style ="width:100%;"></canvas>
  		</div>
  	</div>
  </div>
  </div>
  
  	
<table  class="tablesorter" style="width: 98%">
	<thead >
	<tr><th class="hover"><i class="fas fa-sort-alpha-up" ></i> 회사이름(약자) </th>
	<th class="hover"><i class="fas fa-sort-alpha-up" > </i>대분류</th>
	<th class="hover"><i class="fas fa-sort-alpha-up" > </i>중분류</th>
	<c:if test="${param.v=='dow' }"><th><i class="fa fa-arrows-v" ></i>가격</th></c:if>
	<th  class="hover"><i class="fas fa-sort-numeric-up" ></i>등락율</th></tr>
	</thead>
	<tbody style="width: 85%">
	<c:forEach items="${list }" var="com">
		<tr>
		<td>${com.name }(${com.initial })</td>
		
	<td>${com.classify1 }</td>
	<td>${com.classify2 }</td>
	<c:if test="${param.v=='dow' }"><td>${com.price }</td></c:if>
	<td>${com.val }%</td></tr>
	</c:forEach>
	</tbody>
</table>

</c:if>
</form>
<script type="text/javascript">
window.onload = function TimerStart(){ tid=setInterval('msg_time()',1000) }
function msg_time() {	// 1초씩 카운트
	date = new Date();
	m = "현재 한국시간 :  " +getWorldTime(+9)+"<br> 현재 미국시간 : " +getWorldTime(-5);
	var msg = "<font style='font-weight: bold;'>"+m +"</font>";
	document.all.ViewTimer.innerHTML = msg;


}
function getAmericanDay(tzOffset) { // 24시간제
	  var now = new Date();
	  var tz = now.getTime() + (now.getTimezoneOffset() * 60000) + (tzOffset * 3600000);
	  now.setTime(tz);


	  var s =
		  
	    leadingZeros(now.getMonth() + 1, 2) + '월' +
	    leadingZeros(now.getDate(), 2) + '일 ' 

	  return s;
	}
function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
	}
	
function getWorldTime(tzOffset) { // 24시간제
	  var now = new Date();
	  var tz = now.getTime() + (now.getTimezoneOffset() * 60000) + (tzOffset * 3600000);
	  now.setTime(tz);


	  var s =
	    leadingZeros(now.getFullYear(), 4) + '-' +
	    leadingZeros(now.getMonth() + 1, 2) + '-' +
	    leadingZeros(now.getDate(), 2) + ' ' +

	    leadingZeros(now.getHours(), 2) + ':' +
	    leadingZeros(now.getMinutes(), 2) + ':' +
	    leadingZeros(now.getSeconds(), 2);

	  return s;
	}

function getToday(){
var date = new Date();
var year = date.getFullYear();
var month = ("0" + (1 + date.getMonth())).slice(-2);
var day = ("0" + date.getDate()).slice(-2);

return year +"-"+ month +"-"+ day;
}


var randomColorFactor = function(){
	return Math.round(Math.random()*255)
}
var randomColor = function(opactiy){ //rgba(255,255,255,1)
	return "rgba(" + randomColorFactor() + "," + randomColorFactor() + "," + randomColorFactor() + "," + (opactiy || '.3') +")";
}
$(function(){
	barGraphPrint();
	barGraphPrint2();
	$("th.hover").on({
		//mouseenter : 마우스 커서가 영역안에 들어온 경우
		mouseenter : function(){
			$(this).css("cursor","pointer")	
		}
	})
})
$(".tablesorter").tablesorter({
	widthFixed:false,
	widgets:['zebra','scroller'],
	widgetOptions:{scroller_height:500}
})
$(" th").click(function(){
	console.log($(this).children("div").children("i").attr("class"))
	if($(this).children("div").children("i").attr("class") =="fas fa-sort-alpha-down"){
		$(this).children("div").children("i").removeClass("fas fa-sort-alpha-down")
		$(this).children("div").children("i").addClass("fas fa-sort-alpha-up");
		}
	else if($(this).children("div").children("i").attr("class") =="fas fa-sort-alpha-up"){
		$(this).children("div").children("i").removeClass("fas fa-sort-alpha-up")
		$(this).children("div").children("i").addClass("fas fa-sort-alpha-down");
		
	}
	
	if($(this).children("div").children("i").attr("class") =="fas fa-sort-numeric-down"){
		$(this).children("div").children("i").removeClass("fas fa-sort-numeric-down")
		$(this).children("div").children("i").addClass("fas fa-sort-numeric-up");
		}
	else if($(this).children("div").children("i").attr("class") =="fas fa-sort-numeric-up"){
		$(this).children("div").children("i").removeClass("fas fa-sort-numeric-up")
		$(this).children("div").children("i").addClass("fas fa-sort-numeric-down");
		
	}
			
})


function barGraphPrint2(){
	var rows = JSON.parse('${json2}');
	var regdates = []
	var datas= []
	var colors = []
	$.each(rows,function(index,item)
			{
				regdates[index]=item.data;
				datas[index]=item.cnt;
				colors[index]= randomColor(0.7);
			})
		var chartData = {
			labels :regdates,
			datasets:[{
				type:'line',
				data :regdates,
				borderColor:colors,
				borderWidth :2,
				label :'건수',
				fill :false,
				data :datas
			}
			
			]}
	
	var config = {
		type : 'pie',
		data :chartData,
		options:{
			responsive :true,
			title :{
				display :true,
				text :'${param.classi} 일자별 등락율',
				position :'bottom'
			},
			legend :{display:false},
			scales:{
					xAxes:[{display: true, stacked: true}],
					yAxes:[{display: true, stacked: true}]
			}
			
		}
	}

	
	var ctx =document.getElementById("canvas2").getContext("2d");
	new Chart(ctx,config);
}
function barGraphPrint(){
	var rows = JSON.parse('${json}');
	var regdates = []
	var datas= []
	var colors = []
	$.each(rows,function(index,item)
			{
				regdates[index]=item.data;
				datas[index]=item.cnt;
				colors[index]= randomColor(0.7);
			})
		var chartData = {
			labels :regdates,
			datasets:[{
				type:'line',
				data :regdates,
				borderColor:colors,
				borderWidth :2,
				label :'건수',
				fill :false,
				data :datas
			},
			{
				type:'bar',
				data :regdates,
				backgroundColor:colors,
				borderWidth :2,
				label :'건수',
				fill :false,
				data :datas
			}
			
			]}
	var classify = "${param.classi} (${type })"
	if(classify==""){
		classify="전체"
	}
	var config = {
		type : 'pie',
		data :chartData,
		options:{
			responsive :true,
			title :{
				display :true,
				text :classify+'  등락율 (' +getAmericanDay(-14) + ")",
				position :'bottom'
			},
			legend :{display:false},
			scales:{
					xAxes:[{display: true, stacked: true}],
					yAxes:[{display: true, stacked: true}]
			}
			
		}
	}

	
	var ctx =document.getElementById("canvas").getContext("2d");
	new Chart(ctx,config);
}
</script>
</body>
</html>