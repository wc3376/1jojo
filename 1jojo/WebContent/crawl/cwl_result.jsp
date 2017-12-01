<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%-- <%@ page import="java.text.SimpleDateFormat" %> --%>
<%@ page import="net.crawl.db.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
//	List boardList=(List)request.getAttribute("boardlist");
//	int listcount=((Integer)request.getAttribute("listcount")).intValue();
//	int nowpage=((Integer)request.getAttribute("page")).intValue();
//	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
//	int startpage=((Integer)request.getAttribute("startpage")).intValue();
//	int endpage=((Integer)request.getAttribute("endpage")).intValue();

//	int number = listcount-(nowpage-1)*10;
%>

<html>
<head>
	<title>크롤링 결과</title>
</head>

<body>
<!-- 게시판 리스트 -->
<p>counting : ${search_list_count}</p>
<p>com_No : ${search_com_No}</p>
<table align=center width=600 border="1" cellpadding="0" cellspacing="0">
<%
//if(listcount > 0){
%>

<!-- 레코드가 있으면 -->
<c:if test="${search_list_count > 0 }">

	<tr align="center" valign="middle">
		<td colspan="4">검색 결과</td>
		<td align=right>
			<font size=2>검색 결과 개수 : ${search_list_count }</font>
		</td>
	</tr>
	
	<tr align="center" valign="middle" bordercolor="#333333">
		<td style="font-family:Tahoma;font-size:8pt;" width="8%" height="26">
			<div align="center">검색 번호</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="50%">
			<div align="center">회사명</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="14%">
			<div align="center">우대사항 모음</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="17%">
			<div align="center">지원자격 모음</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="11%">
			<div align="center">링크</div>
		</td>
	</tr>
	
	<%//int number = listcount-(nowpage-1)*10;
//		for(int i=0;i<boardList.size();i++){
//			BoardBean bl=(BoardBean)boardList.get(i);
	%>	 
	 
	<!-- 화면 출력 번호 -->		
<%-- 	<c:set var="num" value="${listcount-(page-1)*10}"/> 	 --%>
	
	<c:forEach var="b" items="${search_list}">	
	
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23" style="font-family:Tahoma;font-size:10pt;">
			<!-- 번호 출력 부분 -->
			<c:out value="${search_com_No}"/>				
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="left">
			
			<a href="${b.com_link}">
				<%--bl.getBOARD_SUBJECT()--%>
				${b.com_name}
			</a>
			</div>
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"></div>
					${b.com_qual}
		</td>
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"></div>
					${b.com_preex}
		</td>	
	</tr>
	
	</c:forEach>
	
	</c:if>
	
	<!-- 레코드가 없으면 -->
	<c:if test="${search_list_count == 0 }">
		<tr align="center" valign="middle">
			<td colspan="4">검색 결과</td>
			<td align=right>
				<font size=2>검색 결과가 없습니다.</font>
			</td>
		</tr>
	</c:if>
	
	<%
//	}
	%>
	
	<tr align="right">
		<td colspan="5">
	   		<a href="./CrawlSaveAction.cr">[검색결과 저장]</a>
		</td>
	</tr>
</table>

</body>
</html>