<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>게시판 답글 쓰기</title></head><body>
<form:form modelAttribute="board" action="reply.shop"   method="post" name="boardform">
  <form:hidden  path="num" />  <form:hidden  path="grp" />
  <form:hidden  path="grplevel" />  <form:hidden  path="grpstep" />
  <table><caption>게시판 답글 등록</caption>
  <tr><td>글쓴이</td><td><input type="text" name="name">
    <font color="red"><form:errors path="name" /></font></td></tr>
  <tr><td>비밀번호</td><td><form:password path="pass" />
    <font color="red"><form:errors path="pass" /></font></td></tr>
  <tr><td>제목</td><td><form:input path="subject" value="RE:${board.subject}"/> 
  <font color="red"><form:errors path="subject" /></font></td></tr>
  <tr><td>내용</td><td><textarea name="content" rows="15" cols="80"></textarea>
   <font color="red"><form:errors path="content" /></font></td></tr>
     <script>CKEDITOR.replace("content", {
    	 filebrowserImageUploadUrl : "imgupload.shop"  });</script>
  <tr><td colspan="2">
  <a href="javascript:document.boardform.submit()">[답변글등록]</a></td></tr>    
  </table></form:form></body></html>
