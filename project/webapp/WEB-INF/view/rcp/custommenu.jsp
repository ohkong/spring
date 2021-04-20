<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>유저 레시피</title>
<style>
img {style="-webkit-user-select: none;margin: auto;background-color: hsl(0, 0%, 90%);transition: background-color 300ms;"}
</style>
</head>
<body>
<input type="button" onclick="location.href='custom.shop'" value="레시피 추가하기">


<script type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>

<h1>${text}</h1>

<form name="searchform" method="get" action="custommenu.shop" >
<input type="hidden" name="cate" value="5">
<input type="hidden" name="pageNum" value="1">

<table><tr>
<th>
	재료
</th>
<td >
<ul class="resultSet" >
	<li class="item"><input type="text" name="raw" ></li> 
	<li class="item"><input type="button" value="재료추가" class="small"  onclick="addraw()" ></li>
</ul>
</td>
</tr>
<tr>
<th width="170px">재료목록</th><td>  
	<ul class="resultSet" id="resultList" style="border: none;">
	<c:forEach items="${raw }" var="r">
	   <li class='item' style='width: 100px ' onclick='$(this).remove()'>
	    <input type='text' name='rawlist' style='border: none;width: 100px'  readonly='readonly' value="${r }">(x)
	   </li>
	</c:forEach>
  	</ul></td>

</tr>
<tr><th class="sel_food" >상세검색하기</th><td colspan="2" align="left">
<p class="sel_food_text sel_food">상세검색하기를 클릭해주세요.</p>
<div class="select_form">
<table style="width: 85%">
	<tr><th>조리방식 선택</th><td>
	<ul class="resultSet" style="width: 100%">
	<li class="item"><input type="checkbox" name="check1" value="끓이기"  <c:if test= "${way.contains('끓이기')}"> checked="checked" </c:if>   ></li>
	<li class="item">끓이기</li>
	<li class="item"><input type="checkbox" name="check1" value="찌기" <c:if test= "${way.contains('찌기')}"> checked="checked" </c:if>   ></li>
	<li class="item">찌기</li>
	<li class="item"><input type="checkbox" name="check1" value="기타" <c:if test= "${way.contains('기타')}"> checked="checked" </c:if> ></li>
	<li class="item">기타</li>
	<li class="item"><input type="checkbox" name="check1" value="굽기" <c:if test= "${way.contains('굽기')}"> checked="checked" </c:if> ></li>
	<li class="item">굽기</li>
	<li class="item"><input type="checkbox" name="check1" value="튀기기" <c:if test= "${way.contains('튀기기')}"> checked="checked" </c:if> ></li>
	<li class="item">튀기기</li>
	<li class="item"><input type="checkbox" name="check1" value="볶기" <c:if test= "${way.contains('볶기')}"> checked="checked" </c:if> ></li>
	<li class="item">볶기</li>
</ul>
	
	</td></tr>
	<tr><th>조리패턴 선택</th><td>
	<ul class="resultSet" style="width: 100%">
	<li class="item"><input type="checkbox" name="check2" value="밥" <c:if test= "${pat.contains('밥')}"> checked="checked" </c:if> ></li>
	<li class="item">밥</li>
	<li class="item"><input type="checkbox" name="check2" value="반찬" <c:if test= "${pat.contains('반찬')}"> checked="checked" </c:if> ></li>
	<li class="item">반찬</li>
	<li class="item">	<input type="checkbox" name="check2" value="국&찌개" <c:if test= "${pat.contains('국&찌개')}"> checked="checked" </c:if> ></li>
	<li class="item">국&찌개</li>
	<li class="item"><input type="checkbox" name="check2" value="일품" <c:if test= "${pat.contains('일품')}"> checked="checked" </c:if> ></li>
	<li class="item">일품</li>
	<li class="item"><input type="checkbox" name="check2" value="기타" <c:if test= "${pat.contains('기타')}"> checked="checked" </c:if> ></li>
	<li class="item">기타</li>
	<li class="item"><input type="checkbox" name="check2" value="후식" <c:if test= "${pat.contains('후식')}"> checked="checked" </c:if> ></li>
	<li class="item">후식</li>
	</ul>
	</td></tr>
	<tr><th>순서</th><td>
	<ul class="resultSet" style="width: 100%">
	<li class="item"><input type="radio" name ="order" value="calorie" <c:if test= "${param.order=='calorie'}"> checked="checked" </c:if>></li>
	<li class="item">칼로리</li>
	<li class="item"><input type="radio" name ="order" value="carbohyd" <c:if test= "${param.order=='carbohyd'}"> checked="checked" </c:if>></li>
	<li class="item">탄수화물</li>
	<li class="item"><input type="radio" name ="order" value="protein" <c:if test= "${param.order=='protein'}"> checked="checked" </c:if>></li>
	<li class="item">단백질</li>
	<li class="item"><input type="radio" name ="order" value="fat" <c:if test= "${param.order=='fat'}"> checked="checked" </c:if>></li>
	<li class="item">지방</li>
	</ul>
	<select name="asc">
		<option value="">낮은순</option>
		<option value="desc">높은순</option>
	</select>
	</td></tr></table>
</div>


</td></tr>

<tr><td colspan="2"><input type="submit" value="검색" class="small" ></td></tr>
</table>
</form>

<form name="f" method="post" action="rcp.shop">
	<input type="hidden" name="no">
	<input type="hidden" name="cate" value="5">
	<table id="myTable" class="tablesorter">
	<thead>
	<tr>
		<th style="width: 100px;">사진</th>
		<th style="width:100px">작성자</th>
		<th>정보</th>
		
	</tr>
	</thead>
	<tbody>
	
<c:forEach items="${list}" var="item" >
	<tr>
	<td><a href="customRcp.shop?no=${item.no}"><img src="file/${item.getRecipe_pic()}" width="100" height="100"></a></td>
	<td>${item.id }</td>
	<td><a href="customRcp.shop?no=${item.no}"  style="color: 'blue'">${item.name }</a><br>${item.raw}</td>
	</tr>
</c:forEach>

</tbody>
		<tr><td colspan="6">
			<c:if test="${pageNum>1 }"><a href="javascript:listpage('${pageNum-1 }')">[이전]</a></c:if>
			<c:if test="${pageNum<=1 }">[이전]</c:if>
			<c:forEach var="a" begin="${startpage }" end="${endpage }">
				<c:if test="${a ==pageNum }">[${a }]</c:if>
				<c:if test="${a !=pageNum }"><a href="javascript:listpage('${a }')">[${a }]</a></c:if>
			</c:forEach>
			<c:if test="${pageNum<maxpage }">
				<a href="javascript:listpage('${pageNum+1 }')">[다음]</a>
			</c:if>
			<c:if test="${pageNum>=maxpage }">[다음]</c:if>
		</td></tr>
</table>
</form>
	<script type="text/javascript">
	function addraw(){
		if(searchform.raw.value==""){
			alert("재료를 입력해주세요.")
			return;
		}
		$("#resultList").append("<li class='item' style='background-color:white;width: 100px '  onclick='$(this).remove()'><input type='text' name='rawlist' style='border: none;width: 90px'  readonly='readonly'   value='"+searchform.raw.value+ "'>x</li>")
		searchform.raw.value = ""
	}
	function listpage(page){
		searchform.pageNum.value=page;
		console.log(searchform.pageNum.value)
		searchform.submit();
	}
	
	$(function(){
		$("#myTable").tablesorter({ sortList: [[0,0], [1,0]] });
		$(".select_form").hide()
		$(".sel_food").click(function(){
			$(".select_form").toggle("fast") //show() ,hide() 번가라가며 실행
			$(".sel_food_text").toggle("fast")
			if($(this).val() =="상세검색"){
				$(this).val("상세검색 숨기기")
			}else{
				$(this).val("상세검색")
			}
			
		})
		$(".sel_food").on({
			//mouseenter : 마우스 커서가 영역안에 들어온 경우
			mouseenter : function(){
				$(this).css("cursor","pointer")	
			}
		})		
		$(".item").click(function(){
			
		})
		
	})
	$(".tablesorter").tablesorter({
	widthFixed:false,
	widgetOptions:{scroller_height:1000}
})
	function test(menu){ 
		f.no.value = menu
		console.log(f.no.value)
		console.log(menu)
		f.submit()
	}
	</script>
</body>
</html>