<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>돈 정리 좀 해라</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#minfo").show();
		$("#dinfo").hide();
		$("#cinfo").hide();
		$(".dayLine").each(function(){
			$(this).hide();
		})
		$("#tab1").addClass("select");
	})
	function disp_div(id,tab){
		if(tab=="tab3"){ //카테고리통계 버튼 누를시 수입칸 설정
			$(".info").each(function(){
				$(this).hide();
			})
			$(".tab").each(function(){
				$(this).removeClass("select");
			})
			$("#cinfo").show();
			$("#in").show();
			$("#tab4").addClass("select");
		}else if(tab=="tab4"||tab=="tab5"){
			if(tab=="tab4"){
				$(".info").each(function(){
					$("#out").hide();
				})
				$(".tab").each(function(){
					$("#tab5").removeClass("select");
				})
				$("#cinfo").show();
				$("#in").show();
				$("#tab4").addClass("select");
			}else if(tab=="tab5"){
				$(".info").each(function(){
					$("#in").hide();
				})
				$(".tab").each(function(){
					$("#tab4").removeClass("select");
				})
				$("#cinfo").show();
				$("#out").show();
				$("#tab5").addClass("select");
			}
		 }
		else{
			$(".info").each(function(){
				$(this).hide();
			})
			$(".tab").each(function(){
				$(this).removeClass("select");
			})
			$("#"+id).show();
			$("#" + tab).addClass("select");
		}
	}
	function list_disp(id){
		$("#" + id).toggle(); // 보였다 안보였다
	}
	
	$(function(){
		$(".up_form").hide()
		$(".updateform").click(function(){
			$(".up_form").toggle("fast") //show() ,hide() 번가라가며 실행
		})
	})

	function win_open2(page,seq){
		var op = "width=530, height=400, left=50, top=150";
		open(page+".shop?seq="+seq,"",op);
	}
	
</script>

<style type="text/css">
	a:hover{background-color: #c58f8f; }
	div.total{
		float: right;
	}
	div.left,div.right{
		float:left;
	}
	
	.select{
		padding : 3px;
		background-color:#c58f8f;
	}
	th>a{
	color : #ffffff;
	}
	
	td>a.white{
		color : black;
	}
	
	.select>a{
		color : #ffffff;
		text-decoration: none;
		font-weight: bold;
	}
	
	div#ttt {
		border:1px solid grey;
	}
	
	body {
		scrollbar-face-color: #F6F6F6;
		scrollbar-highlight-color: #bbbbbb;
		scrollbar-3dlight-color: #FFFFFF;
		scrollbar-shadow-color: #bbbbbb;
		scrollbar-darkshadow-color: #FFFFFF;
		scrollbar-track-color: #FFFFFF;
		scrollbar-arrow-color: #bbbbbb;
		margin-left:"0px"; margin-right:"0px"; margin-top:"0px"; margin-bottom:"0px";
		}

		<%-- 글씨체 관련 태그들--%>
		td {font-family: "돋움"; font-size: 9pt;}
		th {font-family: "돋움"; font-size: 9pt; }
		
		.divDotText {
		overflow:hidden;
		text-overflow:ellipsis;
		}

		A:link {  font-family:"돋움"; text-decoration:none; }
		A:visited { font-family:"돋움"; text-decoration:none; }
		A:active { font-family:"돋움"; text-decoration:none; }
		A:hover { font-family:"돋움";text-decoration:none;}
		.day{
			width:100px; 
			height:30px;
			font-weight: bold;
			font-size:15px;
			font-weight:bold;
			text-align: center;
		}
		.sat{
			color:#529dbc;
		}
		.sun{
			color:red;
		}
		.today_button_div{
			float: right;
		}
		.today_button{
			width: 100px; 
			height:30px;
		}
		.calendar{
			width:100%;
			margin:auto;
		}
		.navigation{
			margin-top:10px;
			margin-bottom:30px;
			text-align: center;
			font-size: 25px;
			vertical-align: middle;
		}
		.calendar_body{
			background-color: #FFFFFF;
			border:1px solid white;
			margin-bottom: 50px;
			border-collapse: collapse;
		}
		.calendar_body .today{
			border:1px solid white;
			height:120px;
			background-color:#c9c9c9;
			text-align: left;
			vertical-align: top;
		}
		.calendar_body .date{
			font-weight: bold;
			font-size: 15px;
			padding-left: 3px;
			padding-top: 3px;
		}
		.calendar_body .sat_day{
			border:1px solid white;
			height:120px;
			background-color:#EFEFEF;
			text-align:left;
			vertical-align: top;
		}
		.calendar_body .sat_day .sat{
			color: #529dbc; 
			font-weight: bold;
			font-size: 15px;
			padding-left: 3px;
			padding-top: 3px;
		}
		.calendar_body .sun_day{
			border:1px solid white;
			height:120px;
			background-color:#EFEFEF;
			text-align: left;
			vertical-align: top;
		}
		.calendar_body .sun_day .sun{
			color: red; 
			font-weight: bold;
			font-size: 15px;
			padding-left: 3px;
			padding-top: 3px;
		}
		.calendar_body .normal_day{
			border:1px solid white;
			height:120px;
			background-color:#EFEFEF;
			vertical-align: top;
			text-align: left;
		}
		.before_after_month{
			margin: 10px;
			font-weight: bold;
		}
		.before_after_year{
			font-weight: bold;
		}
		.this_month{
			margin: 10px;
		}
</style>
</head>
<body>
 <div class="w3-row-padding w3-margin-bottom">
    <div class="w3-half">
      <div class="w3-container w3-padding-16">
        <div class="piecontainer"
        			style="width:100%; border:1px solid #ffffff">
		<canvas id="lmon" style="width:100%;"></canvas>
		<div style="text-align:center">수입 : <fmt:formatNumber value="${lmontot1}" pattern="#,###"/>원 &nbsp;&nbsp; 
			지출 : <fmt:formatNumber value="${lmontot2}" pattern="#,###"/>원 &nbsp;&nbsp;
			<br>변동 금액: <fmt:formatNumber value="${lmonchg}" pattern="#,###"/>원 </div>
      </div>
      </div>
    </div>
    <div class="w3-half">
    	<div class="w3-container w3-padding-16">
          <div class="barcontainer"
          			style="width:100%; border:1px solid #ffffff">
        	<canvas id="tmon" style="width:100%;"></canvas>
        	<div style="text-align:center">수입 : <fmt:formatNumber value="${tmontot1}" pattern="#,###"/>원 &nbsp;&nbsp; 
				지출 : <fmt:formatNumber value="${tmontot2}" pattern="#,###"/>원 &nbsp;&nbsp;
				<br>변동 금액: <fmt:formatNumber value="${tmonchg}" pattern="#,###"/>원</div>
      	  </div>
      </div>
     </div>
   </div>
<br>
<div class="up_form">
<%-- <form:form modelAttribute="cal" method="post" action="input.shop"> --%>
<form method="post" action="input.shop">
	<input type="hidden" name="id" value="${loginUser.id}">
		<table>
			<caption>수입/지출 작성</caption>
			
<%-- 			<tr height="40px"><td>아이디</td><td><form:input path="id" id="id"/> --%>
<%-- 			<font color="red"><form:errors path="id"/></font></td></tr> --%>
			
			<tr><th style="text-align:center"><font color="red">*</font> 날짜</th>
				<td><input type="date" min="2000-01-01" max="2099-12-31" name="date" 
									class="form-control" style="width:50%;"></td></tr>
<!-- 			<tr><th height="40px" style="text-align:center">날짜</th> -->
<!-- 				<td><input type="date" min="2000-01-01" max="2099-12-31" name="date"  -->
<!-- 									class="form-control" style="width:50%;"> -->
<%-- 						<form:errors path="date"/></td></tr> --%>
			
			<tr><th style="text-align:center"><font color="red">*</font> 수입/지출 구분</th>
				<td><select name="iotype" class="custom-select custom-select-sm form-control form-control-sm">
						<option>선택하세요</option>
						<option value="1">수입</option>
						<option value="2">지출</option></select></td></tr>
			<tr><th style="text-align:center"><font color="red">*</font> 분류</th>
					<td><select name="kind" class="custom-select custom-select-sm form-control form-control-sm">
							<option>선택하세요</option>
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
			<tr><th style="text-align:center">상세 내역</th>
				<td><input type="text" name="detail" class="form-control"></td></tr>
<%-- 			<tr height="40px" style="text-align:center"><th>상세 내역</th><td><form:input path="detail" id="detail" class="form-control"/>  --%>
<%-- 	 			<font color="red"><form:errors path="detail"/></font></td></tr> --%>
				
			<tr><th style="text-align:center"><font color="red">*</font> 금액</th>
				<td><input type="text" name="price" class="form-control"></td></tr>
<%-- 			<tr height="40px" style="text-align:center"><th>금액</th><td><form:input path="price" id="price" class="form-control"/>  --%>
<%-- 	 			<font color="red"><form:errors path="price"/></font></td></tr>	 --%>
			
			<tr><td colspan="2" style="text-align:center;"><input type="submit" value="저장" class="small"></td></tr>
		</table>
		</form>
<%-- 	</form:form> --%>
</div>
<div align="left">
	<input type="button" class="updateform" value="가계부 기입">
</div>

<br>
<!-- onmouseover="this.style.background='#c58f8f'" onmouseout="this.style.background='black'" -->
<table>
	<tr><th id="tab1" class="tab" style="text-align:center; width:33%;font-color:#ffffff;"><a href="javascript:disp_div('minfo','tab1')">일자별 내역</a></th>
		<th id="tab2" class="tab" style="text-align:center; width:33%;font-color:#ffffff;"><a href="javascript:disp_div('dinfo','tab2')">상세내역 조회</a></th>
		<th id="tab3" class="tab" style="text-align:center; width:34%;font-color:#ffffff;"><a href="javascript:disp_div('cinfo','tab3')">카테고리별 통계</a></th></tr>
</table>

<%-- 일별 --%>
<div id="dinfo" class="info" style="display:none; width:100%;">
	<div>
		<table>
			<tr><th style="text-align:center; width:33%;">날짜</th>
				<th style="text-align:center; width:33%;">기입건수</th>
				<th style="text-align:center; width:34%;">총사용금액</th></tr>
		<c:forEach var="cal" items="${list}" varStatus="dtat">
			<tr><td style="text-align:center;">
					<a href="javascript:list_disp('dayLine${dtat.index}')">
					${cal.date}</a></td>
				<td style="text-align:center;">${cnt[dtat.index]}</td>
				<td style="text-align:center;"><fmt:formatNumber value="${cal.total}" pattern="#,###"/>원</td></tr>
		<tr id="dayLine${dtat.index}" class="dayLine">
			<td colspan="3" style="text-align:center;">
		<table>
			<tr><th width="25%" style="text-align:center;">수입/지출 구분</th>
				<th width="15%" style="text-align:center;">분류</th>
				<th width="15%" style="text-align:center;">내역</th>
				<th width="20%" style="text-align:center;">금액</th>
				<th width="25%" style="text-align:center;"></th></tr>
			<c:forEach var="callist" items="${cal.dayList}">
			<tr><td style="text-align:center;">${callist.iotype==1?"수입":"지출"}</td>
				<td style="text-align:center;">${callist.kind}</td>
				<td style="text-align:center;">${callist.detail}</td>
				<td style="text-align:center;">
				<fmt:formatNumber value="${callist.price}" pattern="#,###"/>원</td>
				<td><input type="button" class="small" value="수정" onclick="win_open2('calupdate','${callist.seq}')">
					<input type="button" class="small" value="삭제" onclick="location.href='caldelete.shop?seq=${callist.seq}'"></td></tr>
			</c:forEach>
		</table></td></tr>
		</c:forEach>
		</table>
	</div>
</div>
<%-- 월별 --%>
<div id="minfo" class="info">
	<form name="calendarFrm" id="calendarFrm" action="" method="GET">
<div class="calendar" >
<div class="navigation">
<span class="this_month">
			&nbsp;${today_info.search_year}. 
			<c:if test="${today_info.search_month<10}">0</c:if>${today_info.search_month}
		</span>
</div>

<table class="calendar_body">

<thead>
	<tr bgcolor="#CECECE">
		<td class="day sun" >일</td>
		<td class="day" >월</td>
		<td class="day" >화</td>
		<td class="day" >수</td>
		<td class="day" >목</td>
		<td class="day" >금</td>
		<td class="day sat" >토</td></tr>
</thead>
<tbody>
	<tr>
		<c:forEach var="dateList" items="${dateList}" varStatus="date_status"> 
			<c:choose>
				<c:when test="${dateList.value=='today'}">
					<td class="today">
						<div class="date">
							${dateList.date}
						</div>
						<div>
							<c:if test="${datem2[date_status.index].total!=0}">
							수입 : <fmt:formatNumber value="${datem2[date_status.index].dayin}" pattern="#,###"/>
							<br>
							지출 : <fmt:formatNumber value="${datem2[date_status.index].dayout}" pattern="#,###"/> 
							</c:if>
						</div>
					</td>
				</c:when>
				<c:when test="${date_status.index%7==6}">
					<td class="sat_day">
						<div class="sat">
							${dateList.date}
						</div>
						<div>
						<c:if test="${datem2[date_status.index].total!=0}">
							수입 : <fmt:formatNumber value="${datem2[date_status.index].dayin}" pattern="#,###"/>
							<br>
							지출 : <fmt:formatNumber value="${datem2[date_status.index].dayout}" pattern="#,###"/> 
							</c:if>
						</div>
					</td>
					</tr>
				</c:when>
				<c:when test="${date_status.index%7==0}">
						<tr>	
						<td class="sun_day">
							<div class="sun">${dateList.date}</div>
								<div>
							<c:if test="${datem2[date_status.index].total!=0}">
							수입 : <fmt:formatNumber value="${datem2[date_status.index].dayin}" pattern="#,###"/>
							<br>
							지출 : $<fmt:formatNumber value="${datem2[date_status.index].dayout}" pattern="#,###"/> 
							</c:if>
								
								</div>
							</td>
				</c:when>
				<c:otherwise>
					<td class="normal_day">
							<div class="date">${dateList.date}</div>
							<div>
							<c:if test="${datem2[date_status.index].total!=0}">
							수입 : <fmt:formatNumber value="${datem2[date_status.index].dayin}" pattern="#,###"/>
							<br>
							지출 : <fmt:formatNumber value="${datem2[date_status.index].dayout}" pattern="#,###"/> 
							</c:if>
							</div>
					</td>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</tr>
</tbody>

</table>
</div>
</form>
</div>

<%-- 카테고리별 통계 --%>
<div id="cinfo" class="info">
	<table>
		<tr><td id="tab4" class="tab" style="text-align:center; width:50%;"><a href="javascript:disp_div('in','tab4')" class="white">수입</a></td>
			<td id="tab5" class="tab" style="text-align:center; width:50%;"><a href="javascript:disp_div('out','tab5')" class="white">지출</a></td></tr>
	</table>
	<%-- 수입 통계 --%>
	<div id="in" class="info">
		<table>
			<tr><th style="text-align:center; width:33%;">순서</th>
				<th style="text-align:center; width:33%;">카테고리 명</th>
				<th style="text-align:center; width:34%;">금액(원)</th></tr>
			<c:forEach var="kpi" items="${inkplist}" varStatus="kpin">
			<tr><td style="text-align:center;">${kpin.index+1}</td><td style="text-align:center;">${kpi.kind}</td>
				<td style="text-align:center;"><fmt:formatNumber value="${kpi.price}" pattern="#,###"/>원</td></tr>
			</c:forEach>
		</table>
		<canvas id="instat" style="width:100%;"></canvas>
	</div>
	<%-- 지출 통계 --%>
	<div id="out" class="info">
		<table>
			<tr><th style="text-align:center; width:33%;">순서</th>
				<th style="text-align:center; width:33%;">카테고리 명</th>
				<th style="text-align:center; width:34%;">금액(원)</th></tr>
			<c:forEach var="kpo" items="${outkplist}" varStatus="kpout">
			<tr><td style="text-align:center;">${kpout.index+1}</td><td style="text-align:center;">${kpo.kind}</td>
				<td style="text-align:center;"><fmt:formatNumber value="${kpo.price}" pattern="#,###"/>원</td></tr>
			</c:forEach>
		</table>
		<canvas id="outstat" style="width:100%;"></canvas>
	</div>
</div>

<script type="text/javascript"
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js">
</script>
<script>
var randomColorFactor = function(){
	return Math.round(Math.random()*255);
}
var randomColor = function(opa){
	return "rgba("+randomColorFactor() + "," 
			+randomColorFactor() + ","
			+randomColorFactor() + ","
			+ (opa || '.3') + ")";
}
var color1 = randomColor(0.7);
var color2 = randomColor(0.7);
$(function(){
	barGraphPrint1();	//지난달 그래프
	barGraphPrint2();	//이번달 그래프
	pieGraphPrint1();	//수입 통계 그래프
	pieGraphPrint2();	//지출 통계 그래프
})

function barGraphPrint1(){
	var rows = JSON.parse('${json1}');
	var regdates = [];
	var datas = [];
	var colors = [];
	$.each(rows,function(index,item){
		regdates[index] = item.data;
		datas[index] = item.price;
		colors[0] = color1,
		colors[1] = color2
	})
	var chartData = {
		labels : regdates,
		datasets : [{
			type : 'bar',
			label : '금액',
			backgroundColor : colors,
			data : datas
		}]
	}
	var config = {
		type :'bar',
		data : chartData,
		options : {
			responsive : true,
			title : {display : true,
				text : '${today_info.search_month-1}월 수입/지출 합',
				position : 'top',
				fontSize : 20
			},
			legend : {display : false},
			scales : {
				xAxes : [{display : true, stacked : true}],
				yAxes : [{display : true, stacked : true}]
			}
		}
	}
	var ctx = document.getElementById("lmon").getContext("2d");
	new Chart(ctx,config);
}

function barGraphPrint2(){
	var rows = JSON.parse('${json2}');
	var regdates = [];
	var datas = [];
	var colors = [];
	$.each(rows,function(index,item){
		regdates[index] = item.data;
		datas[index] = item.price;
		colors[0] = color1,
		colors[1] = color2
	})
	var chartData = {
		labels : regdates,
		datasets : [{
			type : 'bar',
			label : '금액',
			backgroundColor : colors,
			data : datas
		}]
	}
	var config = {
		type :'bar',
		data : chartData,
		options : {
			responsive : true,
			title : {display : true,
				text : '${today_info.search_month}월 수입/지출 합',
				position : 'top',
				fontSize : 20	
			},
			legend : {display : false},
			scales : {
				xAxes : [{display : true, stacked : true}],
				yAxes : [{display : true, stacked : true}]
			}
		}
	}
	var ctx = document.getElementById("tmon").getContext("2d");
	new Chart(ctx,config);
}
function pieGraphPrint1(){
	var rows = JSON.parse('${json3}');
	var names=[]
	var datas=[]
	var colors=[]
	$.each(rows,function(index,item){
		names[index] = item.kind;
		datas[index] = item.price;
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
		options : {
			responsive : true,
			legend:{position : 'top'},
			title : {
				display : true,
				text : '수입 분류별 통계',
				postion : 'top'
			}
		}
	}
	var ctx = document.getElementById("instat").getContext("2d");
	new Chart(ctx,config);
}
function pieGraphPrint2(){
	var rows = JSON.parse('${json4}');
	var names=[]
	var datas=[]
	var colors=[]
	$.each(rows,function(index,item){
		names[index] = item.kind;
		datas[index] = item.price;
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
		options : {
			responsive : true,
			legend:{position : 'top'},
			title : {
				display : true,
				text : '지출 분류별 통계',
				postion : 'top'
			}
		}
	}
	var ctx = document.getElementById("outstat").getContext("2d");
	new Chart(ctx,config);
}
</script>
</body>
</html>