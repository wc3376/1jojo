package net.crawl.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.action.Action;
import net.board.action.ActionForward;
//import net.board.db.BoardDAO1;
import net.board.db.BoardDAOImpl;
import net.crawl.db.CrawlDAOImpl;
import net.crawl.db.search_list_Bean;
import net.crawl.db.search_qual_Bean;

 public class CrawlGetSavedDataAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		
		ArrayList<search_list_Bean> listOfResult= new ArrayList<search_list_Bean>();
		ArrayList<search_qual_Bean> listOfsearch_qual_Bean= new ArrayList<search_qual_Bean>();

		CrawlDAOImpl crawldao=new CrawlDAOImpl();
		String No=(String)session.getAttribute("No");
		String search_com_No=crawldao.getSearch_com_No(No);
		listOfResult=crawldao.getSearch_list(search_com_No);
		listOfsearch_qual_Bean=crawldao.getSearch_qual(search_com_No);
		
 		session.setAttribute("search_list", listOfResult); //현재 페이지 수
 		session.setAttribute("cwl_qualAndpreex_analysis_result", listOfsearch_qual_Bean); //최대 페이지 수
 		session.setAttribute("search_com_No", search_com_No ); 
 		session.setAttribute("search_list_count", listOfResult.size());
 		
		ActionForward forward= new ActionForward();
	 	forward.setRedirect(false);
 		forward.setPath("/cwl_result.cr");
 		return forward;
	 }
 }