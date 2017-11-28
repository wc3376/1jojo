<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#myPage">Logo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
     <%--   <li>
        <c:choose>
        <c:when test="${sessionScope.id == null}">
        		<a href="./loginForm.jsp">Log-in</a>
    		</c:when>
			<c:when test="${sessionScope.id != null}">
				<a href="./Logout.do">Log-out</a>
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
        </li>  --%>
       
        	<c:if test="${sessionScope.email == null}">
        		 <li> <a href="./loginForm.jsp">Log-in</a></li>
        		  <li><a href="./regForm.jsp">Sign up</a></li>
    		</c:if>
    		
			<c:if test="${sessionScope.email != null}">
				<li><a href="./Logout.do">Log-out</a></li>
				<li><a href="./myPage.jsp">MyPage</a></li>
			</c:if>
		
        <li><a href="#portfolio">PORTFOLIO</a></li>
        <li><a href="#pricing">PRICING</a></li>
        <li><a href="#contact">CONTACT</a></li>
      </ul>
    </div>
  </div>
</nav>
</body>
</html>