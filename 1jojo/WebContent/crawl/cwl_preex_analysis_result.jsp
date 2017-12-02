<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%-- <%@ page import="java.text.SimpleDateFormat" %> --%>
<%@ page import="net.crawl.db.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
	<title>크롤링 결과</title>
	 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
  </style>
</head>

<body>
<div>
<header>
	<c:import url="../member/header.jsp"/>
</header>
</div>
<br>
<br>
<br>
<!-- 게시판 리스트 -->

<c:import url="../member/header.jsp"/>

<c:import url="crawlNav.jsp"/>

<div class="container-fluid">    
  <div class="row content">
	<div class="col-sm-8 text-left"> 
<table class="table table-striped">
<!-- 레코드가 있으면 -->
	<tr align="center" valign="middle">
		<td style="font-family:Tahoma;font-size:8pt;" height="26">
			<div align="center">우대사항</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;">
			<div align="center">단어 출현 빈도수</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="14%">
			<div align="center">단어가 포함된 공고 목록 링크</div>
		</td>
	</tr>
	
	<c:forEach var="b" items="${listOfsearch_qual_Bean}">	
	
	<c:if test="${b.com_prrex != ''}"> <!-- 만약에 우대사항 조건 빈도가 담긴 row라면 -->
		<tr align="center" valign="middle">
			<td height="23" style="font-family:Tahoma;font-size:10pt;">
				<!-- 단어 출력 부분 -->
				<c:out value="${b.com_preex}"/>				
			</td>			
			<td style="font-family:Tahoma;font-size:10pt;">
				<!-- 단어 빈도 -->
						${b.com_frequncy}
			</td>
			<td style="font-family:Tahoma;font-size:10pt;">
				<a href="./cwl_preex_list.cr">
					링크 목록
				</a>
			</td>
		</tr>
	</c:if>
	</c:forEach>
	
	<!-- 레코드가 없으면 -->
	<c:if test="${search_list_count == 0 }">
		<tr align="center" valign="middle">
			<td colspan="4">검색 결과</td>
			<td align=right>
				<font size=2>검색 결과 자체가 없습니다. 맞춤 채용 설정을 확인해보세요.</font>
			</td>
		</tr>
	</c:if>
	
	<c:if test="${countPerWord_Preex == 0 }">
		<tr align="center" valign="middle">
			<td colspan="4">우대사항 검색 결과</td>
			<td align=right>
				<font size=2>우대사항 검색 결과가 없습니다. 검색된 채용공고에는 우대사항으로 볼 수 있는 항목이 없습니다.</font>
			</td>
		</tr>
	</c:if>
	
	<tr align="right">
		<td colspan="3">
			<button type="button" class="btn btn-default" onclick="location.href='./CrawlSaveAction.cr'">검색결과 저장</button>
		</td>
	</tr>
</table>
    </div>
  </div>
</div>
</body>
</html>