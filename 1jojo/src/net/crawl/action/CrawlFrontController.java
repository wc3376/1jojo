package net.crawl.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.crawl.action.*;

public class CrawlFrontController extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		System.out.println("RequestURI=" + RequestURI);
		System.out.println("contextPath=" + contextPath);
		System.out.println("command=" + command);
		
		if (command.equals("/cwl_ready.cr")) { // 맨 처음 크롤링 할 때 들어갈 페이지로 들어가도록 유도하는 command
			action = new Crawlready();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CrawlAddAction.cr")) {// 크롤링 하기 위해 데이터를 추가시키는 과정.
			action = new CrawlAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/cwl_result.cr")) { // 맨 처음 크롤링 할 때 들어갈 페이지로 들어가도록 유도하는 command
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./crawl/cwl_result.jsp");			
		}
		else if (command.equals("/CrawlAnalysisAction.cr")) {// 크롤링 한 데이터를 분석에 들어가는 과정.
			action = new CrawlAnalysisAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/cwl_qualAndpreex_analysis_result.cr")) {// 분석결과 출력하는 페이지로 연결. 최초 크롤링 할 때와 따로 개인마다 저장된 크롤링 데이터 재 출력시 사용.
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./crawl/cwl_qualAndpreex_analysis_result.jsp");			
		}
		else if (command.equals("/CrawlPreexAndqualFilterAction.cr")) {// 특정 단어(들)에 대한 공고가 포함된 업체 리스트 연산. 최초 크롤링 할 때와 따로 개인마다 저장된 크롤링 데이터 재 출력시 사용.
			action = new CrawlPreexAndqualFilterAction();//미완.
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/cwl_filtered_qualAndpreex_list.cr")) { // 특정 단어(들)에 대한 공고가 포함된 업체 리스트 출력
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./crawl/cwl_filtered_qualAndpreex_list.jsp");		
		}
		
		else if (command.equals("/CrawlSaveAction.cr")) {// 크롤링 한 데이터를 DB에 저장하는 과정.
			action = new CrawlSaveAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CrawlGetSavedDataAction.cr")) {// 크롤링 한 데이터를 DB에 꺼내오는 과정. 이때 필요한 것은 session의 No.
			action = new CrawlGetSavedDataAction();//미완.
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CrawlDeleteSavedDataAction.cr")) {// 크롤링 한 데이터를 DB에 꺼내오는 과정. 이때 필요한 것은 session의 No.
			action = new CrawlDeleteSavedDataAction();//미완.
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (forward != null) {
			if (forward.getRedirect()) { // true
				response.sendRedirect(forward.getPath());
			} else { // false 媛믪쟾�떖�씠 媛��뒫�븿
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}// doprocess() end

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}