<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix ="decorator"  uri="http://www.opensymphony.com/sitemesh/decorator" %>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    <c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<title><decorator:title /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="../styles/font-awesome.min.css">
<link rel="stylesheet" href="../styles/framework.css">
<link rel="stylesheet" href="../styles/layout.css">

<style>
	ul.resultSet {
	    list-style:none;
	    margin:0;
	    padding:0;
	}
	
	li.item {
	    margin: 10 10 10 10;
	    padding: 10 10 10 10;
	    border : 0;
	    float: left;
	}
	.test{
	background-color: #eceaeac4;
	}
	.contain{width:100%; height:200px;border:3px solid white; display:flex;}
	div.total{
		float: right;
	}
	div.left,div.right{
		float:left;
	}
	
	.select{
		padding : 3px;
		border-color: #f6755e;
		background-color :#f6755e;
		border: 3px;
	}
	.select>a{
		color : #ffffff;
		text-decoration: none;
		font-weight: bold;
	}
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
  
}
th{
	font-weight: bord;
}
td, th {
  border: 1px solid ;
  text-align: center ;
  vertical-align : middle;
  padding: 8px;
}
.w3-sidebar a {font-family: "Roboto", sans-serif}
body,h1,h2,h3,h4,h5,h6,.w3-wide {font-family: "Montserrat", sans-serif;}
</style>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="http://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"></script>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<script type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="../js/jquery.mobilemenu.js"></script>



<decorator:head />
<body id="top">
<script type="text/javascript">
function win_open(page){
	var op = "width=500, height=350, left=50, top=150";
	open(page+".shop","",op);
}
</script>
<div class="wrapper row0">
  <div id="topbar" class="hoc clear"> 
    <!-- ################################################################################################ -->
	    <form method="post" action="${path }/user/login.shop" name="f">
	    <input type="hidden" name="gender" value="1">
		<input type="hidden" name="sum" value="0">
		<input type="hidden" name="name" value="유효성검증을 위한 파라미터">
		<input type="hidden" name="email" value="valid@aaa.bbb">
		<input type="hidden" name="birthday" value="950629">
	    <ul>
    <c:if test="${empty loginUser }">
	      <li>아이디</li><li><input name="id" type="text" id="loginid" style="background-color: white"></li>
	      <li>비밀번호</li><li><input type="password" name="pass" style="background-color: white"></li>
	      <li><button value="로그인" style="background-color: #111111;"><i class="fas fa-sign-in-alt"></i></button> </li>
	      <li><a href="${path }/user/userEntry.shop" title="sign-in"><i class="fas fa-address-card"></i></a></li>
	      <li><a href="javascript:win_open('${path}/user/idsearch')" title="idsearch"><i class="fas fa-search" ></i></a></li>
	      <li><a href="javascript:win_open('${path}/user/pwsearch')" title="pwsearch"><i class="fas fa-key"></i></a></li>
    </c:if>
    <c:if test="${!empty loginUser}">
    	  <li><h1>${loginUser.name }님 안녕하세요.</h1></li>
	      <li><a href="${path }/user/logout.shop" title="logout"><i class="fas fa-sign-out-alt"></i></a></li>
	      <li><a href="${path }/user/mypage.shop?id=${loginUser.id}" title="myinfo"><i class="fas fa-user-alt"></i></a></li>
	      <li><a href="${path }/user/update.shop?id=${loginUser.id}" title="update"><i class="fas fa-user-cog"></i></a></li>
    </c:if>
	    </ul>
	    </form>
    <!-- ################################################################################################ -->
  </div>
</div>
<div class="wrapper row1">
  <header id="header" class="hoc clear"> 
    <!-- ################################################################################################ -->
    <div id="logo" class="fl_left">
      <h1><a href="${path }/user/main.shop">MuuYaho</a></h1>
    </div>
    <!-- ################################################################################################ -->
  </header>
</div>
<!-- Top Background Image Wrapper -->
<!-- <div class="bgded" style="background-image:url('images/demo/backgrounds/01.png');">  -->
<div class="bgded" > 
  <!-- ################################################################################################ -->
  <div class="wrapper row2" style="height: 20%">
    <nav id="mainav" class="hoc clear"> 
      <!-- ################################################################################################ -->
      <ul class="clear">
        <li class="active"><a href="${path }/user/main.shop">Home</a></li>
        <li><a class="drop" href="#">게시판</a>
          <ul>
            <li><a href="${path }/board/list.shop">중고거래</a></li>
<!--             <li><a href="pages/full-width.html">Full Width</a></li> -->
<!--             <li><a href="pages/sidebar-left.html">Sidebar Left</a></li> -->
<!--             <li><a href="pages/sidebar-right.html">Sidebar Right</a></li> -->
<!--             <li><a href="pages/basic-grid.html">Basic Grid</a></li> -->
          </ul>
        </li>
        <li><a class="drop" href="#">밥먹쟈</a>
          <ul>
            <li><a href="${path }/rcp/menu.shop">뭐먹찌?</a></li>
            <li><a href="${path }/rcp/custommenu.shop">회원레시피</a></li>
            <li><a href="${path }/nutrition/myNutrition.shop">밥은 먹고다니냐?</a></li>
          </ul>
        </li>
        <li><a class="drop" href="#">내돈보신분 9함..</a>
          <ul>
        	<li><a href="${path }/company/onepage.shop">주식어때</a></li>
	        <li><a href="${path}/cal/cal.shop">돈 정리 좀 해라</a></li>
    	    <li><a href="${path}/cal/bank.shop">목돈 좀 쌓아볼래?</a></li>
          </ul>
</li>
      </ul>
      <!-- ################################################################################################ -->
    </nav>
  </div>
  <!-- ################################################################################################ -->
</div>
<div class="wrapper row3" style="background-color: #fbc4c4">
  <section class="hoc container clear"> 
    <!-- ################################################################################################ -->
<!--     <div class="sectiontitle"> -->
<!--       <h6 class="heading">MUHAN DOUZUEN</h6> -->
<!--       <p>Did you hear about Infinite Chalenge</p> -->
<%-- 			<h3><decorator:title></decorator:title></h3> --%>
<!--     </div> -->
  <div  style="width: 100%">
  	    <div class="w3-container w3-card  w3-round w3-margin" style="text-align:right;background-color: #ffebeb">
  		<br><br>
  	    <h5 style="font-weight: bord;" align="center"><decorator:title></decorator:title></h5>
  	    <br><br>
    <decorator:body></decorator:body>
    </div>
  
  </div>
    <!-- ################################################################################################ -->
    <div class="clear"></div>
  </section>
</div>
<!-- ################################################################################################ -->
<!-- <div class="wrapper bgded overlay light" style="background-image:url('images/demo/backgrounds/05.png');"> -->
<div class="wrapper bgded overlay light" >
  <figure class="hoc clear"> 
    <!-- ################################################################################################ -->
    <ul class="nospace group logos">
<!--       <li class="one_quarter first"><a href="#"><img src="images/demo/222x100.png" alt=""></a></li> -->
<!--       <li class="one_quarter"><a href="#"><img src="images/demo/222x100.png" alt=""></a></li> -->
<!--       <li class="one_quarter"><a href="#"><img src="images/demo/222x100.png" alt=""></a></li> -->
<!--       <li class="one_quarter"><a href="#"><img src="images/demo/222x100.png" alt=""></a></li> -->
    </ul>
    <!-- ################################################################################################ -->
  </figure>
</div>
<div class="wrapper row5">
  <div id="copyright" class="hoc clear"> 
    <!-- ################################################################################################ -->
    <p class="fl_left">Copyright &copy; 2018 - All Rights Reserved - <a href="#">Domain Name</a></p>
    <p class="fl_right">Template by <a target="_blank" href="https://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>
    <!-- ################################################################################################ -->
  </div>
</div>
<a id="backtotop" href="#top"><i class="fa fa-chevron-up"></i></a>
</body>
</html>