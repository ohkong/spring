<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html><html><head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<script type="text/javascript">
    function file_delete() {
    	document.f.fileurl.value = ""
	    file_desc.style.display = "none";
    }
</script></head><body>
<form:form modelAttribute="board" action="update.shop"
   enctype="multipart/form-data" name="boardform">
   <form:hidden path="num" />
   <table><tr><td>글쓴이</td><td><form:input path="name" />
   <font color="red"><form:errors path="name" /></font></td></tr>
   <tr><td>비밀번호</td><td><form:password path="pass" />
   <font color="red"><form:errors path="pass" /></font></td></tr>
   <tr><td>제목</td><td><form:input path="subject" />
   <font color="red"><form:errors path="subject" /></font></td></tr>
   
   <tr><th>구매 or 판매</th><td>
	<input type="radio" name="buy" value="0" <c:if test= "${pat.contains('구매')}"> checked="checked" </c:if> >구매
	<input type="radio" name="buy" value="1" <c:if test= "${pat.contains('판매')}"> checked="checked" </c:if> >판매
	</td></tr>

	<tr><th>거래장소</th><td>
	<form:input path="location" />
    <font color="red"><form:errors path="location" /></font>
    <input type="button" id ="checkMap" name="save" value="저장" <c:if test= "${pat.contatins('저장')}"> checked="checked" </c:if> >
    </td></tr>
    
	
   <tr><td>금액</td><td><form:input path="price" />
   <font color="red"><form:errors path="price" /></font></td></tr>
   
   
   
   
   <tr><td>내용</td><td><form:textarea path="content" rows="15" cols="80" />
   <script>CKEDITOR.replace("content", {
     filebrowserImageUploadUrl : "imgupload.shop"
   });
   </script>
   <font color="red"><form:errors path="content" /></font></td></tr>
   <tr><td>첨부파일</td><td><c:if test="${!empty board.fileurl}">
     <div id="file_desc">
       <a href="file/${board.fileurl}">${board.fileurl}</a>
       <a href="javascript:file_delete()">[첨부파일삭제]</a>
     </div></c:if>
   <form:hidden path="fileurl"/>  
   <input type="file" name="file1"></td></tr>
   <tr><td colspan="2">
    <a href="javascript:document.boardform.submit()">[게시글수정]</a>
    <a href="list.shop">[게시글목록]</a>
    </td></tr></table></form:form>
    
    <!-- 카카오 map api 부분 -->
<div id="map" style="width:100%;height:350px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fe789b62ea841ab553d4d582f80e5b69&libraries=services"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
$("#checkMap").click(function(){
var v = $("#location").val()
geocoder.addressSearch(v, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
     
        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+v+'</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
}); 
})
</script>
    
    </body></html>