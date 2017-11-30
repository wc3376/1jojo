<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	$(document).ready(function(){
		$("form").submit(function(){			
			if($("#passwd").val()==""){
				alert("비밀번호를 입력하세요");
				$("#passwd").focus();
				return false;
			}	
		});			
	});
</script>
<body>
<div>
<header>

<c:import url ="header.jsp" />


</header>
<br>
<br>
<br>

</div>
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="/171122m2/UpdateForm.do">정보 수정</a></p>
      <p><a href="./searchRecord.jsp">검색 기록</a></p>
      <p><a href="/171122m2/DeleteMember.do">회원 탈퇴</a></p>
    </div>
    <div class="col-sm-8 text-left"> 
      <center><h1>회원 탈퇴</h1></center>

		<form method=post action="./Delete.do">
			<input type=hidden name="email" value="${sessionScope.email}">
				<table border=1 align=center width=300>
					<tr><td>ID</td>
					<td>${sessionScope.email}</td>
					<tr><td>비밀번호</td>
					<td><input type=password id="passwd" name="pass"></td>
					</tr>
					<tr><td colspan=2 align=center>
					<input type=submit value="회원탈퇴">
					<input type=reset value="취소">
			        </td>
		            </tr>		
				</table>
 		</form>
    </div>
 
  </div>
</div>



</body>
</html>