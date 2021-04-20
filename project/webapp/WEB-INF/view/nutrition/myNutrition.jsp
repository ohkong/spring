<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 영양정보 확인하기</title>

</head>
<body>
    <c:set var="path" value="${pageContext.request.contextPath }" />
<script type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="../js/widget-scroller.js"></script>
<input type="button" class="input_food" value="섭취식품 입력하기">
<div class="input_form">
<div style="border: 1px;height : 200px;;border-style: solid;">
<form name="fo" method="post" action="addEatfood.shop" onsubmit="return test()">
	<input type="hidden" name="cate" value="6">
	<h1 align="center" style="font-weight: bord">섭취식품 입력하기</h1>
	<ul class="resultSet">
	<li class="item"><input type="date" name="date" ></li>
	<li class="item"><input type="hidden" name="fodidx" ></li>
	<li class="item"><input type="text" name="fod" onclick="win_open('addForm')" readonly="readonly" placeholder="여기를 클릭하세요 " ></li>
	<li class="item"><input type="submit" value="추가하기" class="small" ></li>
	</ul>
	<h1></h1><h1></h1>
</form>
</div>
</div>

<form>
<div style="width: 100%;height :220px;background-color: #eceaeac4" align="center">
	<br><br><br>
<strong style="font-size: 50px;">${month }월</strong>
<br><br>
<select name="month" onchange="submit()" style="width: 25%" class="custom-select custom-select-sm form-control form-control-sm">
		<option>월 선택하기</option>
	<c:forEach begin="1" end="12" var="i">
		<option value="${i }" style="text-align: center" >${i }월 보기 </option>
	</c:forEach>
</select>
<br><br>
</div>
</form>
<div class="ctr_button" ></div>
	<div class="contain" style="height: 50px;width: 100%;display: none;background-color: white">
			<c:forEach begin="1" end="7" var="j" >
			<div  style="height: 50px;width: 100%">
			
			<c:if test="${j<=start }">
				<div >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</c:if>
			<c:if test="${j>start }">
				<div class="ctr_button" >
				<c:choose>
					<c:when test="${(j-start)%7==0 }">sun<br></c:when>
					<c:when test="${(j-start)%7==1 }">mon<br></c:when>
					<c:when test="${(j-start)%7==2 }">tue<br></c:when>
					<c:when test="${(j-start)%7==3 }">wed<br></c:when>
					<c:when test="${(j-start)%7==4 }">tur<br></c:when>
					<c:when test="${(j-start)%7==5 }">fri<br></c:when>
					<c:when test="${(j-start)%7==6 }">sat<br></c:when>
				</c:choose>
					${j-start }<br>
					${list[j-start-1].getTotal().calorie } kcal
				</div>
			</c:if>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>
		<button onclick="nextday(${1})" style="border: none;background-color: white"><p style="color: black">▶</p></button>
	</div>
	<c:forEach begin="1" end="5" var="i">
	  	<div class="contain" style="height: 50px;width: 100%;display: none;background-color: white">
		<c:if test="${i!=0}"><button onclick="nextday(${i-1})" style="border: none;background-color: white"><p style="color: black">◀</p></button></c:if>
		<c:forEach begin="0" end="6" var="j" >
			<div style="width: 15%">
			<c:if test="${j+(i*7)-start+1<end+1 }">
			<div class="ctr_button" >
			
							<c:choose>
					<c:when test="${(j+(i*7)-start+1)%7==0 }">sun<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==1 }">mon<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==2 }">tue<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==3 }">wed<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==4 }">tur<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==5 }">fri<br></c:when>
					<c:when test="${(j+(i*7)-start+1)%7==6 }">sat<br></c:when>
				</c:choose>
			
			
			
			${j+(i*7)-start+1 }<br>
			${list[j+(i*7)-start].getTotal().calorie } kcal
			</div>
			</c:if>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>  
		<c:if test="${i!=5 }"><button onclick="nextday(${i+1})" style="border: none;background-color: white"><p style="color: black">▶</p></button></c:if>
		
		</div>	
	
	</c:forEach>

	<div style="width: 100%;height: 80%px;background-color: white;padding-top: 50px">
	  <div class="w3-row-padding w3-margin-bottom" style="width:100%;height: 100%">
	  <c:if test="${loginUser.weight!=0.0 && loginUser.height!=0.0 }">
	  <h1>${loginUser.name }님의 권장 섭취 칼로리량 
	  
	  
	  <c:if test ="${ loginUser.gender==1 || loginUser.gender==3}">
	  	${ Math.round( 662-(9.53*(2021 - loginUser.birthday.split("-")[0] +1)) +(15.91*loginUser.weight+ 539.6*loginUser.height)) }kcal
	  </c:if>
	  <c:if test ="${ loginUser.gender==2 || loginUser.gender==4}">
	  	${ Math.round( 354-(6.91*(2021 - loginUser.birthday.split("-")[0] +1)) +(9.36*loginUser.weight+ 726*loginUser.height)) }kcal
	  	
	  </c:if>
	  </h1>
	  </c:if>
	   <c:if test="${loginUser.weight==0.0 || loginUser.height==0.0 }">
	   	<h1>회원정보에서 키와 몸무게를 수정해주세요</h1><a href="../user/update.shop?id=${loginUser.id }">회원정보로 이동하기</a>
	   
	   </c:if>
  <div class="w3-half">
  		<div id ="piecontainer" style="width:100%;">
  			<canvas id = "canvas" style ="width:100%;"></canvas>
  		</div>
  		<div id ="str" style="width:100%"></div>
  </div>
  <div class="w3-half">
  		<div id ="tablecontainer" style="width:100%;">
  		</div>
  </div>
  </div>
	
	
	</div>
  	<form action="delEatfood.shop" method="post" name="delform">
  	<input type="hidden" name="cate" value="6">
  		<input type="hidden" name="no" id="no">
				</form>
<%-- minfo : 회원 정보 출력 --%>
	<script type="text/javascript">
	function del(no){
		$("input#no").val(no)
		console.log($("input#no").val())
		delform.submit()
	}
	function test(){
		if(fo.date.value=="" ){
			alert("날짜를입력해주세요.")
			return false;
		}
		if(fo.fod.value==""){
			alert("음식을 입력해주세요.")
			return false;
		}
		return true
	}
		fo.date.valueAsDate = new Date();
	   function win_open(page) {
		   var op = "width=500, height=350, left=50,top=150";
		   open(page+".shop","",op);
	   }
	$(".ctr_button").each(function(index){
			$(this).attr("idx",index); 
			$(this).addClass("test");
		}).click(function(){		   
			var index =$(this).attr("idx");
			
			moveSlider(index)
		})   
	$(".contain").each(function(index){
			$(this).attr("idx",index); //idx 속성 등록 : 0 ~ 4까지 
		})  
	$(document).ready(function(){
		$("#minfo").show();
		$(".saleLine").each(function(){
			$(this).hide();
		})
		$(".ctr_button[idx=0]").hide()
		$(".contain[idx="+"${week}"+"]").show()
		$(".contain[idx!="+"${week}"+"]").hide()
		$(".input_food").click(function(){
			$(".input_form").toggle("fast") 
			if($(this).val() =="섭취식품 입력하기"){
				$(this).val("섭취식품 입력 숨기기")
			}else{
				$(this).val("섭취식품 입력하기")
			}
		})
		$(".ctr_button").on({
			//mouseenter : 마우스 커서가 영역안에 들어온 경우
			mouseenter : function(){
				$(this).css("cursor","pointer")	
			}
		})		
		$(".input_form").hide()
		moveSlider("${day}")
	})
	function disp_div(id,tab){
		$(".info").each(function(){
			$(this).hide();
		})
		$("#"+id).show();
	}
	function list_disp(id){
		$("#"+id).toggle();
	}
	function nextday(index){
		$(".contain[idx="+index +"]").show()
		$(".contain[idx!="+index +"]").hide()
	}
	function moveSlider(index){
		var m = ${month}
		m = m<10?"0"+m:m
		d = index<10?"0"+index:index
		day = "2021-"+m+"-"+d
		tableindex = index-1
		$(".ctr_button").each(function(index){
			$(this).addClass("test"); //idx 속성 등록 : 0 ~ 4까지 
		})
		$(".ctr_button[idx!="+index +"]").removeClass("select")
		$(".ctr_button[idx="+index +"]").removeClass("test");
		$(".ctr_button[idx="+index +"]").addClass("select");

		$.ajax({
			url:"${path}/ajax/nuttable.shop?day="+day,
			dataType :'JSON',
			success : function(data){
				$("#tablecontainer").html(data[0])
				$("#str").html(data[2])
				$(".chartjs-size-monitor").remove()
				pieGraphPrint(data[1]);
			
			},
			error :function(e){
				alert("테이블 조회시 서버오류:"+e.status);
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
				text :'일일 영양소 섭취비율',
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
</script>
</body>
</html>