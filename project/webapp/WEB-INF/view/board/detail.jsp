<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>게시물 상세보기</title>
<style type="text/css">
  .leftcol {	text-align: left;	vertical-align : top;  }
.lefttoptable {  height : 250px;	    border-width: 0px;
	text-align: left;	vertical-align : top;	padding: 0px; }
</style></head><body>
<table><tr><td colspan="2">중고나라 게시판</td></tr>
   <tr><td width="15%">글쓴이</td>
       <td width="85%" class="leftcol">${board.name}</td></tr>
   <tr><td>제목</td><td class="leftcol">${board.subject}</td></tr>
   <tr><td>금액</td><td class="rightcol">${board.price }</td></tr>
   <tr><td>등록일</td><td class="rightcol">${board.regdate }</td></tr>
<%--    <tr><td>상태</td><td class="rightcol">${board.btype }</td></tr> --%>
   <!--  <input type="radio" name="sell" value="판매중" /> 
   <input type="radio" name="buy" value="구매중" /> 
   <input type="radio" name="complete" value="거래완료" /> -->
   
   <tr><td>내용</td><td class="leftcol">
     <table class="lefttoptable">
<tr><td class="leftcol lefttoptable">${board.content}</td></tr></table></td></tr>
   <tr><td>첨부파일</td><td>&nbsp;
    <c:if test="${!empty board.fileurl}">
     <a href="file/${board.fileurl}">${board.fileurl}</a>
    </c:if></td></tr>
   <tr><td colspan="2">
     <a href="reply.shop?num=${board.num}">[답변]</a>
     <a href="update.shop?num=${board.num}">[수정]</a>
     <a href="delete.shop?num=${board.num}">[삭제]</a>
     <a href="list.shop">[게시물목록]</a>
   </td></tr></table>
   <div id="map" style="width:100%;height:350px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fe789b62ea841ab553d4d582f80e5b69&libraries=services"></script>
   <script type="text/javascript">
   var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
   mapOption = {
       center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
       level: 3 // 지도의 확대 레벨
   };  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//지도에 표시할 원을 생성합니다
var circle = new kakao.maps.Circle({
    center : new kakao.maps.LatLng(33.450701, 126.570667),  // 원의 중심좌표 입니다 
    radius: 50, // 미터 단위의 원의 반지름입니다 
    strokeWeight: 5, // 선의 두께입니다 
    strokeColor: '#75B8FA', // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'dashed', // 선의 스타일 입니다
    fillColor: '#CFE7FF', // 채우기 색깔입니다
    fillOpacity: 0.7  // 채우기 불투명도 입니다   
}); 

// 지도에 원을 표시합니다 
circle.setMap(map); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
   var v = $("#location").val()
   geocoder.addressSearch("${board.location}", function(result, status) {
		console.log("${board.location}")
       // 정상적으로 검색이 완료됐으면 
        if (status === kakao.maps.services.Status.OK) {

           var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

           // 결과값으로 받은 위치를 마커로 표시합니다
           var marker = new kakao.maps.Marker({
               map: map,
               position: coords
           });
           var infowindow = new kakao.maps.InfoWindow({
               content: '<div style="width:150px;text-align:center;padding:6px 0;">거래장소</div>'
           });
           infowindow.open(map, marker);

           // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
           map.setCenter(coords);
       } 
   }); 
   </script>
   
   
   
   </body></html>