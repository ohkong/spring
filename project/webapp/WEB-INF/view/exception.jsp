<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
  <%-- isErrorPage="true" : exception 객체가 내장 객체로 할당됨. 
  	exception 내장객체 할당 : 실제로 발생된 예외 객체
  	--%>

<script>
	alert("${exception.message}")
	location.href="${exception.url}"
</script>