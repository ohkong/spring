<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/layout/userlayout.jsp --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%-- path : /jspstudy2 --%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title><decorator:title /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
<link rel="stylesheet" href="${path}/css/main.css">
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<decorator:head />
</head>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>Menu</button>
  <span class="w3-bar-item w3-right">
  <c:if test="${empty sessionScope.loginUser}">
  	<a href="${path}/user/login.shop">�α���</a>
  	<a href="${path}/user/userEntry.shop">ȸ������</a>
  </c:if>
  <c:if test="${!empty sessionScope.loginUser}">
  	${sessionScope.loginUser.username}���� �α��� �ϼ̽��ϴ�.&nbsp;&nbsp;
  	<a href="${path}/user/logout.shop">�α׾ƿ�</a>
  </c:if>
  </span>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-row">
    <div class="w3-col s4">
    <c:if test="${!empty sessionScope.loginUser}">
      <img src="${path}img/logo.jpg" class="w3-circle w3-margin-right" style="width:46px">
      </c:if>
      <c:if test="${empty sessionScope.loginUser}">
      <img src="" class="w3-circle w3-margin-right" style="width:46px">
      </c:if>
    </div>
    <div class="w3-col s8 w3-bar">
      <c:if test="${!empty sessionScope.loginUser}">
      <span>�ݰ����ϴ�. <strong>${sessionScope.loginUser.username}��</strong></span>
      </c:if>
       <c:if test="${empty sessionScope.loginUser}">
      <span><strong>�α����ϼ���</strong></span>
      </c:if>
      <br>
    </div>
  </div>
  <hr>
  <div class="w3-container">
    <h5>Dashboard</h5>
  </div>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>Close Menu</a>
     <a href="${path}/user/main.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding w3-text-teal"><i class="fa fa-th-large fa-fw w3-margin-right"></i>ȸ������</a> 
    <a href="${path}/item/list.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw w3-margin-right"></i>��ǰ����</a> 
    <a href="${path}/board/list.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw w3-margin-right"></i>�Խ���</a> 
    <a href="${path}/chat/chat.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-envelope fa-fw w3-margin-right"></i>ä��</a>
  </div>
  <div class="w3-container">
  <%-- ajax�� ���� ���� ȯ�� ���� ���� ��� --%>
  	<div id="exchange"></div>
  </div><br>
  <div class="w3-display-container">
  	<div id="exchange2"></div>
  </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" 
			style="cursor:pointer" title="close side menu" id="myOverlay">
			
</div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">

  <!-- Header -->
  <header class="w3-container" style="padding-top:22px">
  	<h5><b><i class="fa fa-dashboard"></i>���� ������</b></h5>
  	
   </header>

   <div class="w3-row-padding w3-margin-bottom">
    <div class="w3-half">
      <div class="w3-container w3-padding-16">
        <div class="piecontainer"
        			style="width:100%; border:1px solid #ffffff">
        	<canvas id="canvas1" style="width:100%;"></canvas>
      </div>
      </div>
    </div>
    <div class="w3-half">
    	<div class="w3-container w3-padding-16">
          <div class="barcontainer"
          			style="width:100%; border:1px solid #ffffff">
        	<canvas id="canvas2" style="width:100%;"></canvas>
      	  </div>
      </div>
     </div>
   </div>
    
  
  
  <hr>
  
  <div class="w3-container">
  <decorator:body />
  </div>
  <hr>

  
  <hr>
  <br>
  

  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
  
  </footer>

  <!-- End page content -->
  <div>
  <div class="w3-light-grey w3-center w3-padding-24">�����ī���� Since 2016</div>
</div>
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>
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
	$(function(){
		piegraph();	//�۾��̺� ��� �Խñ� �Ǽ�.
		bargraph();	//�ֱ� 7�ϰ� �ۼ��� �Խñ� �Ǽ�
		exchangeRate();	//���������� ȯ������
		exchangeRate2();// KEB �ϳ����� ȯ������
	})
	function piegraph(){
		$.ajax("${path}/ajax/graph.shop",{
			success:function(data){
			//	console.log(data);
				pieGraphPrint(data);
			},
			error : function(e){
				alert("���� ����:" + e.status);
			}
		})		
	}
	function bargraph(){
		$.ajax("${path}/ajax/graph2.shop",{
			success : function(data){
				barGraphPrint(data);
			},
			error : function(e){
				alert("���� ����:" + e.status);
			}
		})		
	}
	function exchangeRate(){
		$.ajax("${path}/ajax/exchange.shop",{
			success : function(data){
				$("#exchange").html(data);
			},
			error : function(e){
				alert("ȯ�� ��ȸ�� ���� ����:"+e.status);
			}
		})
	}
	function exchangeRate2(){
		$.ajax("${path}/ajax/exchange2.shop",{
			success : function(data){
				$("#exchange2").html(data);
			},
			error : function(e){
				alert("ȯ�� ��ȸ�� ���� ����:"+e.status);
			}
		})
	}
	function pieGraphPrint(data){
		var rows = JSON.parse(data);
		var names=[]
		var datas=[]
		var colors=[]
		$.each(rows,function(index,item){
			names[index] = item.name;
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
			options : {
				responsive : true,
				legend:{position : 'top'},
				title : {
					display : true,
					text : '�۾��� �� �Խ��� ��� �Ǽ�',
					postion : 'bottom'
				}
			}
		}
		var ctx = document.getElementById("canvas1").getContext("2d");
		new Chart(ctx,config);
	}
	function barGraphPrint(data){
		var rows = JSON.parse(data);
		var regdates = [];
		var datas = [];
		var colors = [];
		$.each(rows,function(index,item){
			regdates[index] = item.regdate;
			datas[index] = item.cnt;
			colors[index] = randomColor(0.7);
		})
		var chartData = {
			labels : regdates,
			datasets : [{
				type : 'line',
				borderWidth : 2,
				borderColor : colors,
				label : '�Ǽ�',
				fill : false,
				data : datas
			},{
				type : 'bar',
				label : '�Ǽ�',
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
					text : '�ֱ� 7�� �Խ��� ��� �Ǽ�',
					position : 'bottom'	
				},
				legend : {display : false},
				scales : {
					xAxes : [{display : true, stacked : true}],
					yAxes : [{display : true, stacked : true}]
				}
			}
		}
		var ctx = document.getElementById("canvas2").getContext("2d");
		new Chart(ctx,config);
	}
</script>
</body>
</html>
