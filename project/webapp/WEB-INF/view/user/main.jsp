<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>메인화면</title>
</head>
<body>
	<div id="ViewTimer"></div>
  <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="piecontainer" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  			<canvas id = "barcanvas1" style ="width:100%;"></canvas>
  		</div>
  	</div>
  </div>
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="barcontainer" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  			<canvas id = "barcanvas2" style ="width:100%;"></canvas>
  		</div>
  	</div>
  </div>
  </div>
  <c:if test="${!empty loginUser }">
  <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="piecontainer" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  			<canvas id="thistot" style="width:50%;"></canvas>
  			<h1></h1>
  		</div>
  	</div>
  </div>
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="barcontainer" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
			<canvas id = "canvas" style ="width:100%;"></canvas>
			<div align="center">권장  탄수화물 55-65%, 단백질 7-20%, 지방 15-30%</div>
  		</div>
  	</div>
  </div>
  </div>
  </c:if>
  
  <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id ="recommendRcp" style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  		</div>
  		
  	</div>
  </div>
  <div class="w3-half">
  	<div class="w3-container w3 -padding-16">
  		<div id =recommendBoard style="width:100%;border:1px solid #ffffff;background-color: #fffef7;">
  		</div>
  	</div>
  </div>
  </div>
  
   <c:set var="path" value="${pageContext.request.contextPath }" />
<script type="text/javascript">

window.onload = function TimerStart(){ tid=setInterval('msg_time()',1000) }

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
function getAmericanDay(tzOffset) { // 24시간제
	  var now = new Date();
	  var tz = now.getTime() + (now.getTimezoneOffset() * 60000) + (tzOffset * 3600000);
	  now.setTime(tz);


	  var s =
		  
	    leadingZeros(now.getMonth() + 1, 2) + '월' +
	    leadingZeros(now.getDate(), 2) + '일 ' 

	  return s;
	}

	
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
		date = new Date();
		m = "현재 한국시간 :  " +getWorldTime(+9)+"<br> 현재 미국시간 : " +getWorldTime(-5);
		var msg = "<font style='font-weight: bold;'>"+m +"</font>";
		document.all.ViewTimer.innerHTML = msg;


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


function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year +"-"+ month +"-"+ day;
}
function getMonth(){
	var date = new Date();
    return ("0" + (1 + date.getMonth())).slice(-2);
}
var user= '${loginUser}'
	$.ajax({
		url:"${path}/ajax/todaySector.shop?day="+getToday(),
		success : function(data){
			barGraphPrint(data);
		},
		error :function(e){
			alert("S&P 500 조회시 서버오류:"+e.status);
		}
	})
	$.ajax({
		url:"${path}/ajax/todaySector2.shop?day="+getToday(),
		success : function(data){
			barGraphPrint2(data);
		},
		error :function(e){
			alert("다우존스 조회시 서버오류:"+e.status);
		}
	})
	
	$.ajax({
		url:"${path}/ajax/recommendRcp.shop",
		success : function(data){
			console.log(data)
			$("#recommendRcp").html(data+"<p align='center'>레시피 순위</p>")
			
		},
		error :function(e){
			alert("테이블 조회시 서버오류:"+e.status);
		}
	})
	
	$.ajax({
		url:"${path}/ajax/recommendBoard.shop",
		success : function(data){
			console.log(data)
			$("#recommendBoard").html(data+"<p align='center'>HOT 게시글</p>")
			
		},
		error :function(e){
			alert("테이블 조회시 서버오류:"+e.status);
		}
	})
	
	
if(user!=""){
$.ajax({
		url:"${path}/ajax/thistot.shop",
		success : function(data){
		pieGraphPrintCal(data);
		},
		error :function(e){
		alert("용돈조회시 서버오류:"+e.status);
		}
	})
$.ajax({
	url:"${path}/ajax/nuttable.shop?day="+getToday(),
	dataType :'JSON',
	success : function(data){
		pieGraphPrint(data[1]);
	},
	error :function(e){
		alert("영양정보 조회시 서버오류:"+e.status);
	}
})
}

var randomColorFactor = function(){
	return Math.round(Math.random()*255);
}
var randomColor = function(opactiy){ //rgba(255,255,255,1)
	return "rgba(" + randomColorFactor() + "," + randomColorFactor() + "," + randomColorFactor() + "," + (opactiy || '.3') +")";
}

function pieGraphPrint(data){
	var rows = JSON.parse(data);
	var regdates = []
	var datas= []
	var colors = []
	$.each(rows,function(index,item)
			{
				regdates[index]=item.data;
				datas[index]=item.cnt;
				colors[index]= randomColor(0.7);
			})
			
	var config = {
		type : 'pie',
		data :{
			datasets:[{
				data :datas,
				backgroundColor:colors
			}],
			labels :regdates
		},
		options:{
			responsive :true,
			title :{
				display :true,
				text :getToday()+' 영양소 섭취비율',
				position :'bottom'
			},
			legend :{display:'top'},
			
		}
	}

	
	var ctx =document.getElementById("canvas").getContext("2d");
	try{
		chart.data.datasets[0].data = datas;
		chart.update();
	}catch(exception){
	chart=new Chart(ctx,config);
	}
}
function barGraphPrint(data){
	var rows = JSON.parse(data);
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
	
	var config = {
		type : 'pie',
		data :chartData,
		options:{
			responsive :true,
			title :{
				display :true,
				text :'S&P 500  등락율 ( 기준  '+getAmericanDay(-14)+")",
				position :'bottom'
			},
			legend :{display:false},
			scales:{
					xAxes:[{display: true, stacked: true}],
					yAxes:[{display: true, stacked: true}]
			}
			
		}
	}

	
	var ctx =document.getElementById("barcanvas1").getContext("2d");
	new Chart(ctx,config);
}
function barGraphPrint2(data){
	var rows = JSON.parse(data);
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
	
	var config = {
		type : 'pie',
		data :chartData,
		options:{
			responsive :true,
			title :{
				display :true,
				text :'다우 존스  등락율 ( 기준 '+getAmericanDay(-14)+")",
				position :'bottom'
			},
			legend :{display:false},
			scales:{
					xAxes:[{display: true, stacked: true}],
					yAxes:[{display: true, stacked: true}]
			}
			
		}
	}

	
	var ctx =document.getElementById("barcanvas2").getContext("2d");
	new Chart(ctx,config);
}


function pieGraphPrintCal(data){
	var rows = JSON.parse(data);
	var names=[]
	var datas=[]
	var colors=[]
	$.each(rows,function(index,item){
		names[index] = item.data;
		datas[index] = item.cnt;
		colors[index] = randomColor(0.7);
	})
	var config = {
		type : 'pie',
		data : {
			datasets : [{
				data: datas,
				backgroundColor : colors
			}],
			labels : names
		},
		options:{
			responsive :true,
			title :{
				display :true,
				text :getMonth()+'월 사용 금액',
				position :'bottom'
			},
			legend :{display:'top'},
		}
	}
	var ctx = document.getElementById("thistot").getContext("2d");
	new Chart(ctx,config);
}


</script>
</body>
</html>