package net.crawl.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.board.db.BoardDAOImpl;
import net.crawl.db.CrawlDAOImpl;
import net.crawl.db.search_list_Bean;
import net.crawl.db.search_qual_Bean;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class CrawlAnalysisAction implements Action {
	CrawlDAOImpl crawldao = new CrawlDAOImpl();

	public ArrayList<String> removeCategory(String targetText) {
		String[] tempText = targetText.split("-"); // 일단 문자열을 -기준으로 나눈다. 빈곳은 고려 않는다.
//		String regex1 = "^[가-힣]*$"; // check category
//		String regex2 = "^-\\s\\S*\\s:\\s$"; // check item
		ArrayList<String> listOfToken= new ArrayList<String>();
		for (int i = 0; i < tempText.length; i++) {
			String s = tempText[i];
			// System.out.println("itme : " + s);
			if(s.contains(":")) {
				listOfToken.add(s.substring(s.indexOf(":")).trim());
			}
			String tokens[]=null;
			if(s.contains(",")) {
				tokens = s.split(",");
				for(String token: tokens) {
					listOfToken.add(token.trim());
				}
			}
		}
		return listOfToken;
	}

	@SuppressWarnings("unchecked")
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CrawlAnalysisAction");
		ActionForward forward = new ActionForward();
		CrawlDAOImpl crawldao=new CrawlDAOImpl();

		try {
			response.setContentType("text/html; charset=utf-8");//출
			request.setCharacterEncoding("utf-8");//입
			List<search_list_Bean> listOfsearch_list= new ArrayList<search_list_Bean>();
//			HttpSession session = request.getSession();

//			load test
			System.out.println( request.getAttribute("search_list")) ;
			System.out.println("search_list_count"+  request.getSession().getAttribute("search_list_count")) ;
			System.out.println(request.getSession().getAttribute("search_list"));
//			if(request.getAttribute("search_list") == null)
//				System.out.println("비었다.");
			
			listOfsearch_list= (ArrayList<search_list_Bean>) request.getAttribute("search_list");
//			load test
			
			String totalQual="";
			String totalPreex="";
			for(search_list_Bean listData : listOfsearch_list) {
				totalQual  +=listData.getCom_qual();
				totalPreex +=listData.getCom_preex();
			}
			ArrayList<String> listOfToken= new ArrayList<String>();
			listOfToken = removeCategory(totalQual);//유의미한 단어만 별도 추출
			System.out.println(listOfToken);
			HashMap<String, Integer> countPerWord_Qual = new HashMap<String, Integer>();//단어, 빈도
			for (String word : listOfToken) {
				if(countPerWord_Qual.get(word)==null) {
					countPerWord_Qual.put(word, 1);
				}else {
					countPerWord_Qual.put(word, countPerWord_Qual.get(word)+1);
				}				
			}
			
			listOfToken = removeCategory(totalPreex);//유의미한 단어만 별도 추출
			System.out.println(listOfToken);
			HashMap<String, Integer> countPerWord_Preex = new HashMap<String, Integer>();//단어, 빈도
			for (String word : listOfToken) {
				if(countPerWord_Preex.get(word)==null) {
					countPerWord_Preex.put(word, 1);
				}else {
					countPerWord_Preex.put(word, countPerWord_Preex.get(word)+1);
				}				
			}
//			search_com_No NUMBER,
//			No NUMBER, //일단 연동이 어느정도 마무리되면 한다.
//			com_qual VARCHAR2(1000),
//			com_preex VARCHAR2(1000),
//			com_frequency NUMBER,
		    Iterator<String> iterator1 = countPerWord_Qual.keySet().iterator();
		    while (iterator1.hasNext()) {
		    	String key = (String) iterator1.next();
		    	search_qual_Bean qual = new search_qual_Bean();
				qual.setSearch_com_No(listOfsearch_list.get(0).getSearch_com_No() );
				qual.setCom_frequncy(countPerWord_Qual.get(key));
		    }
		    Iterator<String> iterator2 = countPerWord_Qual.keySet().iterator();
		    while (iterator2.hasNext()) {
		    	String key = (String) iterator2.next();
		    	search_qual_Bean qual = new search_qual_Bean();
				qual.setSearch_com_No(listOfsearch_list.get(0).getSearch_com_No() );
				qual.setCom_frequncy(countPerWord_Qual.get(key));
		    }
			request.setAttribute("search_list", listOfsearch_list); //검색결과 리스트를 세션으로 전송
			System.out.println("session updated");

			forward.setRedirect(false);
			forward.setPath("./cwl_qual_analysis_result.cr");
			return forward;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("문제가 생겼다. alert!");
			PrintWriter out =response.getWriter();
			out.println("<script>");
			out.println("alert('단어분석 과정에서 문제가 생겼습니다. 에러 내용 : "+ex+"');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		return null;
	}
}