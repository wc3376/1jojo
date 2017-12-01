<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="m2member.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Scraping Ready</title>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<div>
<header>
	<c:import url="../member/header.jsp"/>
</header>
</div>
<br>
<br>
<h2>ID및 비밀번호</h2>
<form action="/1jojo/CrawlAddAction.cr" method="post"  name="boardform" ><%-- ${세션 데이터 SET된 것 가져와야함.} --%>
		아이디 : <input type="text" name="id" required="required" disabled="disabled" value="chlgudrbdn"><p> <%-- ${email} --%>
		암호 : <input type="password" name="password" required="required" disabled="disabled" value="m6529194!"><p><%-- ${pass} --%>
	<p>해당 id가 실제로 사람인에서 사용하는 id와 비밀번호 입니까?</p>
	<p>(아니라면 사람인 회원가입 이후 다시 해당 ID와 비밀번호로 해당 서비스에 가입한 뒤 이 페이지로 접속이 요구됩니다.)</p>
	<p>회원정보 변경은 상단의 탭에 위치해 있습니다.</p>
	<p style="red"><b>주의 (필독!)</b></p>
	<ol>
		<li>해당 서비스는 사람인으로 부터 인가받은 서비스가 아닌 개인적인 프로젝트 테스트 및 포트폴리오용으로 작성된 코드입니다.
		<li>때문에 보안이 취약할 수 밖에 없으므로 해당 서비스에 쓸 용도로 쓸 임시 비밀번호 설정이 필요합니다.
		<li>어디까지나 취준생의 편의라는 공익를 위해 만든 것이며 개인 하드에만 해당 정보를 넣는 것 외에는 스크래핑은 불법이 될 소지가 큽니다. 자신이 이 사이트의 프로그램을 이용해 크롤링한 데이터를 웹에 배포하지 않도록 주의 바랍니다.
		<li>SNS 계정 접속, 네이버 계정, 구글 계정 접속과 같은 SSO 기능은 제공하지 않습니다. 해당 계정으로 가입하셨다면, 사람인 회원 가입을 다시 한 뒤에 이 서비스에 다시 접속하셔서 아이디와 비밀번호를 변경후에 다시 시도 바랍니다.
		<li>해당 스크래핑은 페이지 구조가 일정한 형식을 따르지 않나, 검색 결과의 수가 많아지면 상당한 시간이 소요됩니다.
	</ol>
	<input type="submit" value="예(스크래핑 시작)">
	<button type="button" onclick="window.open('https://www.saramin.co.kr/zf_user/member/registration/join')">아니오(사람인 회원가입)</button>
</form>




</body>
</html>