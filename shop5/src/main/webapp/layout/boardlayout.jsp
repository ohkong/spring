<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /WebContent/layout/boardlayout.jsp --%>
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
body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
</style>
<script type="text/javascript" src=
	"http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="${path}/css/main.css">
<script type="text/javascript"
		src="http://cdn.ckeditor.com/4.5.7/standard/ckeditor.js">
</script>
<decorator:head />
</head>
<body class="w3-light-grey w3-content" style="max-width:1600px">

<!-- Top container -->
<div class="w3-bar w3-top w3-hover-grey" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" 
  						onclick="w3_open();"><i class="fa fa-bars"></i>Menu</button>
  <span class="w3-bar-item w3-right">
  <c:if test="${empty sessionScope.loginUser}">
  	<a href="${path}/user/login.shop">로그인</a>
  	<a href="${path}/user/userEntry.shop">회원가입</a>
  </c:if>
  <c:if test="${!empty sessionScope.loginUser}">
  	${sessionScope.loginUser.username}님이 로그인 하셨습니다.&nbsp;&nbsp;
  	<a href="${path}/user/logout.shop">로그아웃</a>
  </c:if>
  </span>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-light-grey w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container">
    <a href="${path}/img/logo.jpg" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
     <c:if test="${!empty sessionScope.loginUser}">
    <img src="${sessionScope.picture}" style="width:45%;" class="w3-round">
    </c:if>
    <c:if test="${empty sessionScope.loginUser}">
      <img src="" class="w3-round" style="width:45%">
      </c:if>
      <div class="w3-wide s8 w3-bar">
      <c:if test="${!empty sessionScope.loginUser}">
      <span>Welcome, <strong>${sessionScope.loginUser.username}</strong></span>
      </c:if>
       <c:if test="${empty sessionScope.loginUser}">
      <span><strong>로그인하세요</strong></span>
      </c:if>
      <br>
    </div>
    <br><br>
   <div class="w3-wide">
    <h5>Dashboard</h5>
  	</div>
  </div>
  <div class="w3-bar-block">
    <a href="${path}/user/main.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding w3-text-teal"><i class="fa fa-th-large fa-fw w3-margin-right"></i>회원관리</a> 
    <a href="${path}/item/list.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw w3-margin-right"></i>상품관리</a> 
    <a href="${path}/board/list.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw w3-margin-right"></i>게시판</a> 
    <a href="${path}/chat/chat.shop" onclick="w3_close()" class="w3-bar-item w3-button w3-padding"><i class="fa fa-envelope fa-fw w3-margin-right"></i>채팅</a>
  </div>
  
</nav>

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" 
	style="cursor:pointer" title="close side menu" id="myOverlay">
	
</div>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-pale-green" style="margin-left:300px">

  <!-- Header -->
  <header id="portfolio">
  <div class="w3-container">
    <h1><b></b></h1>
    <div class="w3-section w3-bottombar w3-padding-16">
      
    </div>
    </div>
  </header>

  <!-- Pagination -->
  <div class="w3-center w3-padding-32">
    <div class="w3-bar">
      <decorator:body />
    </div>
  </div>

  <!-- Footer -->
  <footer class="w3-container w3-padding-32">
 
  </footer>
  
  <div class="w3-pale-green w3-hover-pale-red w3-center w3-padding-24">구디아카데미 Since 2016</div>

<!-- End page content -->
</div>

<script>
// Script to open and close sidebar
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}
</script>

</body>
</html>
