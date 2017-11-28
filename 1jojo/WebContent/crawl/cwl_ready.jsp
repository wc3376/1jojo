<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="m2member.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Scraping Ready</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>

<h2>ID및 비밀번호</h2>
<form action="/1jojo/CrawlAddAction.cr" method="post" 
	enctype="multipart/form-data" name="boardform" >
		아이디 : <input type="text" name="id" required="required" disabled="disabled" value="chlgudrbdn"><p> <%-- ${세션 데이터 SET된 것 가져와야함.} --%>
		암호 : <input type="password" name="password" required="required" disabled="disabled" value="m6529194!"><p>
	<p>해당 id가 실제로 사람인에서 사용하는 id와 비밀번호 입니까?</p>
	<p>(아니라면 사람인 회원가입 이후 다시 해당 ID와 비밀번호로 해당 서비스에 가입한 뒤 이 페이지로 접속이 요구됩니다.)</p>
	<input type="submit" value="예(스크래핑 시작)">
	<button type="button" onclick="window.open('https://www.saramin.co.kr/zf_user/member/registration/join')">아니오(사람인 회원가입)</button>
</form>
</body>
</html>