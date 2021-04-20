<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    <c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>S&P 500 정보</h1>
<c:if test= "${param.classi !='' and param.classi != null}" >
	<h1>${type } 의 평균 = ${avg } </h1>
</c:if>
<c:if test= "${param.classi == null}" >
	<h1>전체 평균 = ${avg } </h1>
</c:if>
  	<div class="w3-container w3 -padding-16">
  		<div id ="barcontainer" style="width:80%;border:1px solid #ffffff">
  			<canvas id = "canvas" style ="width:100%;"></canvas>
  		</div>
  	</div>
<table>
<tr><th>회사이름</th><th>중분류</th><th>등락율</th></tr>
<c:forEach items="${list }" var="str">
	<tr>
	<td>${str.name }</td>
	<td>${str.classify2 }</td>
	<td>${str.val }</td>
	</tr>
</c:forEach>
</table>
<script type="text/javascript">
var randomColorFactor = function(){
	return Math.round(Math.random()*255)
}
var randomColor = function(opactiy){ //rgba(255,255,255,1)
	return "rgba(" + randomColorFactor() + "," + randomColorFactor() + "," + randomColorFactor() + "," + (opactiy || '.3') +")";
}
$(function(){
	bargraph();
})

	function bargraph(){
		$.ajax("${path}/ajax/bargraph.shop?classi=${param.classi}",{
			success : function(data){
				barGraphPrint(data);
				console.log(data)
			},
			error :function(e){
				alert("서버오류:"+e.status);
			}
		})
	
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
				text :'${param.classi}의 금일등락율',
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