<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>레시피 입력하기</title>
</head>
<body>
<script type="text/javascript">
console.log("${rcp.no}")
</script>
<form method="post" id="uploadForm"  enctype="multipart/form-data" ></form>
<table id="preview" class="content">

<tr><th>레시피명</th><td colspan="2"><input type="text" name="name" id="name" value="${menu}"></td></tr>
<tr><th>재료입력</th><td colspan="2"><input type="text" id="raw" width="95%" value="${rcp.raw}"></td></tr>
<tr>
<td rowspan="4">		
	<div class="inputArea">
	 <label for="gdsImg"></label>
<!-- 	 <input type="file" class="gdsImg" id="foodPic" /> -->
	 <div class="select_img"><img src="" /></div>
	</div>
</td></tr>

<tr><th>레시피방식</th><td>
 	<ul class="resultSet" style="width: 100%">
	<li class="item"><input type="radio" id="recway" value="끓이기" <c:if test="${rcp.recway =='끓이기'}">checked="checked" </c:if> ></li>
	<li class="item">끓이기</li>
	<li class="item"><input type="radio" id="recway" value="찌기" <c:if test="${rcp.recway =='찌기'}">checked="checked" </c:if>  ></li>
	<li class="item">찌기</li>
	<li class="item"><input type="radio" id="recway" value="기타" <c:if test="${rcp.recway =='기타'}">checked="checked" </c:if> ></li>
	<li class="item">기타</li>
	<li class="item"><input type="radio" id="recway" value="굽기" <c:if test="${rcp.recway =='굽기'}">checked="checked" </c:if> ></li>
	<li class="item">굽기</li>
	<li class="item"><input type="radio" id="recway" value="튀기기" <c:if test="${rcp.recway =='튀기기'}">checked="checked" </c:if> ></li>
	<li class="item">튀기기</li>
	<li class="item"><input type="radio" id="recway" value="볶기" <c:if test="${rcp.recway =='볶기'}">checked="checked" </c:if> ></li>
	<li class="item">볶기</li>
</ul>
</td>
</tr>
<tr><th>조리패턴</th><td> 
	<ul class="resultSet" style="width: 100%">
	<li class="item"><input type="radio" id="recpat" value="밥"  <c:if test="${rcp.recpat =='밥'}">checked="checked" </c:if>  ></li>
	<li class="item">밥</li>
	<li class="item"><input type="radio" id="recpat" value="반찬" <c:if test="${rcp.recpat =='반찬'}">checked="checked" </c:if> ></li>
	<li class="item">반찬</li>
	<li class="item">	<input type="radio" id="recpat" value="국&찌개" <c:if test="${rcp.recpat =='국&찌개'}">checked="checked" </c:if> ></li>
	<li class="item">국&찌개</li>
	<li class="item"><input type="radio" id="recpat" value="일품" <c:if test="${rcp.recpat =='일품'}">checked="checked" </c:if> ></li>
	<li class="item">일품</li>
	<li class="item"><input type="radio" id="recpat" value="기타" <c:if test="${rcp.recpat =='기타'}">checked="checked" </c:if> ></li>
	<li class="item">기타</li>
	<li class="item"><input type="radio" id="recpat" value="후식" <c:if test="${rcp.recpat =='후식'}">checked="checked" </c:if> ></li>
	<li class="item">후식</li>
	</ul></td></tr>
	
<tr>
	<td colspan="3">
        <div class="body">
            <!-- 첨부 버튼 -->
            <div id="attach">
                <label class="waves-effect waves-teal btn-flat" for="uploadInputBox">조리 순서 추가</label>
                <input id="uploadInputBox" style="display: none" type="file" name="filedata" multiple />
            </div>
        </div>
	</td>
	
</tr>
<c:forEach begin = "0" end="${length}" var="i">
<tr>
	<td><textarea class='recipe'  rows='5px'>${rec[i] }</textarea></td>
	<td colspan="2"><img src="file/${recp[i] }" style="height: 150px"></img></td>
</tr>
</c:forEach>

</table>
<br>
<button class="submit"><a href="#" title="등록"  class="btnlink"><p style="color: black;vertical-align: middle;">등록</p></a></button>
<br><br><br>
<input type="hidden" id="chFoodPic" value="0">
<input type="hidden" id="chrecipePic" value="0">
	 <script>
	  $(".gdsImg").change(function(){
	   if(this.files && this.files[0]) {
	    var reader = new FileReader;
	    reader.onload = function(data) {
	     $(".select_img img").attr("src", data.target.result).height(200);        
	    }
	    $("#chFoodPic").val(1)
	    console.log(this.files[0])
	    reader.readAsDataURL(this.files[0]);
	   }
	  });
	  
	  var files = {};
      var previewIndex = 0;
      // image preview 기능 구현
      // input = file object[]
      function addPreview(input) {
          if (input[0].files) {
              //파일 선택이 여러개였을 시의 대응
              for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
                  var file = input[0].files[fileIndex];

                  if (validation(file.name))
                      continue;

                  var reader = new FileReader();
                  reader.onload = function(img) {
                      //div id="preview" 내에 동적코드추가.
                      //이 부분을 수정해서 이미지 링크 외 파일명, 사이즈 등의 부가설명을 할 수 있을 것이다.
                      var imgNum = previewIndex++;
                      $("#preview")
                              .append(
                                      "<tr class=\"preview-box\" value=\"" + imgNum +"\">"
                                              + "<td><textarea class='recipe'  rows='5px'></textarea></td>"
                                              +"<td colspan='2'><ul class='resultSet'>  <li class='item'> <img class=\"thumbnail\" style='height: 150px'  src=\"  " + img.target.result + "\"\/></li>"
                                              + "<li class='item'><p>"
                                              + file.name
                                              + "</p></li>"
                                              + "<li class='item'><a href=\"#\" value=\""
                                              + imgNum
                                              + "\" onclick=\"deletePreview(this)\">"
                                              + "삭제" + "</a></li></ul></td>" + "</tr>");
                      files[imgNum] = file;
                      $("#chrecipePic").val(1)
                  };
                  
                  reader.readAsDataURL(file);
              }
          } else
              alert('invalid file input'); 
      }


      //preview 영역에서 삭제 버튼 클릭시 해당 미리보기이미지 영역 삭제
      function deletePreview(obj) {
          var imgNum = obj.attributes['value'].value;
          delete files[imgNum];
          $("#preview .preview-box[value=" + imgNum + "]").remove();
          resizeHeight();
      }

      //client-side validation
      //always server-side validation required
      function validation(fileName) {
          fileName = fileName + "";
          var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
          var fileNameExtension = fileName.toLowerCase().substring(
                  fileNameExtensionIndex, fileName.length);
          if (!((fileNameExtension === 'jpg')
                  || (fileNameExtension === 'gif') || (fileNameExtension === 'png'))) {
              alert('jpg, gif, png 확장자만 업로드 가능합니다.');
              return true;
          } else {
              return false;
          }
      }
      $(document).ready(function() {
    	  $(".select_img img").attr("src", "file/${rcp.getRecipe_pic()}").height(200);      
		
    	  $('.submit a').on('click',function() {                        
              var form = $('#uploadForm')[0];
              var formData = new FormData(form);
  
//               for (var index = 0; index < Object.keys(files).length; index++) {
//                   formData.append('recipePicList',files[index]);
//               }
                  formData.append('name',$("input#name").val());
                  formData.append('raw',$("input#raw").val());
                 
                  formData.append('recway',$("input#recway").val());
                  formData.append('recpat',$("input#recpat").val());
                  formData.append('no',"${rcp.no }");
                  
                  $('textarea.recipe').each(function(index,item){
                	  formData.append('recipeList',this.value);
                  })
              //ajax 통신으로 multipart form을 전송한다.
              if(inputCheck()){
              $.ajax({
                  type : 'POST',
                  enctype : 'multipart/form-data',
                  processData : false,
                  contentType : false,
                  cache : false,
                  timeout : 600000,
                  url : '../ajax/recipeupdate.shop',
                  dataType : 'JSON',
                  data : formData,
                  success : function(result) {
                      //이 부분을 수정해서 다양한 행동을 할 수 있으며,
                      //여기서는 데이터를 전송받았다면 순수하게 OK 만을 보내기로 하였다.
                      //-1 = 잘못된 확장자 업로드, -2 = 용량초과, 그외 = 성공(1)
                      if (result === -1) {
                          alert('jpg, gif, png, bmp 확장자만 업로드 가능합니다.');
                          // 이후 동작 ...
                      } else if (result === -2) {
                          alert('파일이 10MB를 초과하였습니다.');
                          // 이후 동작 ...
                      } else {
                          alert('이미지 업로드 성공');
                          location.href="custommenu.shop";
                      }
                  }
              });
          }
                  
                  
          });
          $('#attach input[type=file]').change(function() {
              addPreview($(this)); //preview form 추가하기
          });
      });
      function inputCheck(){
//           if($("input#recpat").val()==""){
//     		  alert("레시피 타입을 입력해주세요.")
//     		  return false;
//           }
//           if($("input#name").val()==""){
//     		  alert("레시피 이름을 입력해주세요.")
//     		  return false;
//           }
//           if($("input#raw").val()==""){
//     		  alert("레시피 재료을 입력해주세요.")
//     		  return false;
//           }
//           if($("input#recway").val()==""){
//     		  alert("레시피 방식을 입력해주세요.")
//     		  return false;
//           }
//           if($("input#recpat").val()==""){
//     		  alert("레시피 사진을 입력해주세요.")
//     		  return false;
//           }
//     	  if($("#chrecipePic").val()==0){
//     		  alert("하나 이상의 레시피 제작과정 사진이 필요합니다.")
//     		  return false;
//     	  }
    	  return true;
      }
	 </script>
</body>
</html>