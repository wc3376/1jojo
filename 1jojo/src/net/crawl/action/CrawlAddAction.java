package net.crawl.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import net.crawl.db.CrawlDAOImpl;
import net.crawl.db.search_re_com_list_Bean;
import net.crawl.db.search_re_com_qual_Bean;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

	@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class CrawlAddAction implements Action {
	CrawlDAOImpl boarddao = new CrawlDAOImpl();
	search_re_com_list_Bean listData = new search_re_com_list_Bean();
	search_re_com_qual_Bean qualData = new search_re_com_qual_Bean();

	private static WebDriver driver;
	String Title = null;
	String URL = null;
	String alertText = "";
	static String path ="";

	public String show_detail_contents(String javascript_link) { //살짝 문제가 있는데 ajax로 인해 다른 페이지 정보까지 몇개 끌고올수 있다.
		String link;
		String temp = javascript_link.substring(javascript_link.indexOf("(") + 1, javascript_link.indexOf(")") - 1);
		temp = temp.replace("'", "");
		temp = temp.replace(" ", "");
		// System.out.println(temp);

		String[] params = temp.split(",", -1);
		// params[0]=rec_idx, params[1]=recommend_ids, params[2]=t_content,
		// params[3]=list_seq, params[4]=last_param
		// for(String s : params) System.out.println(s);// for check
		if (params[4].equals("null")) {
			params[1] = "none";
		}
		link = "/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
				+ params[3] + "&inner_term=" + params[2] + "&view_type=tailor&rec_idx=" + params[0] + "&recommend_ids="
				+ params[1] + "&t_ref=suited_list&t_ref_content=" + params[2];
		/*
		 * origianl js function // function show_detail_contents(rec_idx, recommend_ids,
		 * t_content, list_seq, last_param) // { // var openNewWindow = window.open();
		 * // if (last_param == 'none') // recommend_ids = "none"; //
		 * openNewWindow.location.href =
		 * "/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
		 * // +list_seq+"&inner_term="+t_content+"&view_type=tailor&rec_idx="+rec_idx+
		 * "&recommend_ids="+recommend_ids+"&t_ref=suited_list&t_ref_content="+
		 * t_content; // } System.out.println(link);//check
		 */
		return link;
	}

/*	public String removeSpace(String targetText) {// 공백제거용.
		targetText = targetText.replace(" ", "");
		return targetText;
	}*/

	public void categorizer_with_recruit_guideline_preferred(String targetText) {
		String preex = "";
		String qual = "";

		String[] tempText = targetText.split("\n", -1); // 일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
		String regex1 = "^[가-힣]*$"; // check category
		String regex2 = "^- .*$"; // check item

		for (int i = 0; i < tempText.length; i++) {
			String s = tempText[i];
//			System.out.println("itme : " + s);

			if (s.matches(regex1)) {

				if (s.contains("우대")) {
					// System.out.print("우대이하 파트를 읽는다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
//						System.out.println("string : " + s);
						preex += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					System.out.println("우대사항: " + preex);
					listData.setCom_preex(listData.getCom_preex() +preex);
				}

				if (s.contains("자격")) {
					// System.out.print("자격이다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
//						System.out.println("string : " + s);
						qual += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					System.out.println("자격요건: " + qual);
					listData.setCom_qual(qual);
				}

				if (s.contains("필수")) {
					// System.out.print("필수다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
//						System.out.println("string : " + s);
						qual += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					System.out.println("필수요건: " + qual);
					listData.setCom_qual(qual);
				}
			} else if (s.isEmpty()) { // check enter //equals==""나 "\n"나 "\\"론 안됨.
				// System.out.println("이것은 공백");
				continue;
			}
		}
	}
	 private void categorizer_with_temptb(String tableText) {
		 System.out.println("temptb 있는 경우-속성이 위에 있는 경우");
		 System.out.println("tableText : "+tableText);
		 String preex = "";
		 String qual = "";

		String[] tempText = tableText.split("\n", -1); // 일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
//		String regex1 = "^[가-힣]*$"; // check category
		String regex2 = "^- .*$"; // check item

			for (int i = 0; i < tempText.length; i++) {
				String s = tempText[i];
//				System.out.println("itme : " + s);
//				if (s.matches(regex1)) {
					if (s.contains("우대")) {
						// System.out.print("우대이하 파트를 읽는다! \n");
						i++;
						s = tempText[i];
						while (s.matches(regex2)) {
//							System.out.println("string : " + s);
							preex += s;
							i++;
							if (i < tempText.length) {
								s = tempText[i];
							} else {
								break;
							}
						}
						System.out.println("우대사항: " + preex);
						listData.setCom_preex(listData.getCom_preex() + preex);
					}

					if (s.contains("자격")) {
						// System.out.print("자격이다! \n");
						i++;
						s = tempText[i];
						while (s.matches(regex2)) {
//							System.out.println("string : " + s);
							qual += s;
							i++;
							if (i < tempText.length) {
								s = tempText[i];
							} else {
								break;
							}
						}
						System.out.println("자격요건: " + qual);
						listData.setCom_qual(qual);
					}

					if (s.contains("필수")) {
						// System.out.print("필수다! \n");
						i++;
						s = tempText[i];
						while (s.matches(regex2)) {
//							System.out.println("string : " + s);
							qual += s;
							i++;
							if (i < tempText.length) {
								s = tempText[i];
							} else {
								break;
							}
						}
						System.out.println("필수요건: " + qual);
						listData.setCom_qual(qual);
					}
			}
	}
		private void categorizer_only_table(WebElement table) {
			// TODO Auto-generated method stub
			 System.out.println("테이블만 있는 경우-속성이 위에 있는 경우");
			 System.out.println("tableText : "+table.getText());

 			if( table.findElement(By.tagName("tr")).getText().contains("자격") ||
 					table.findElement(By.tagName("tr")).getText().contains("요건")) {//행에 자격 또는 요건이란 단어가 있는 table을 거른다.
 				List<WebElement> tr_list = table.findElements(By.tagName("tr"));
 				int colNo =0; // 현재 읽고 있는 열의 숫자. 행의 숫자는 필요 없음.
 				int colNo_with_qual = 0; // 자격 요건이란 정보가 담긴 열의 숫자.
 				boolean isThead=true;
 				for(WebElement tr : tr_list) {
 					List<WebElement> td_list = tr.findElements(By.tagName("td"));
 					if( isThead == true) {
 						for(WebElement td : td_list ) {
 							System.out.println("colNo"+colNo +",colNo_with_qual "+colNo_with_qual+ ", td.getText()"+td.getText());
 							if( td.getText().contains("자격") || td.getText().contains("요건")) {
 		        				colNo_with_qual = colNo;
 							}
 							++colNo;
 						}
 						isThead=false;
 					}
 					colNo=0; //초기화
 					for(WebElement td : td_list ) {
							if(colNo_with_qual == colNo) {
								System.out.println("사실 temptb는 아니고 table만 갖고 parsing함. 메소드만 빌려씀.");
								categorizer_with_temptb (td.getText());
							}
							colNo++;
						}
 				}
 			}else{
 				System.out.println("예외 : 유의미한 정보가 없어보이는 데이터.");
 			}
		}
	private void categorizer_only_detail_meta(String tableText) {
		 System.out.println("detail_meta만 있는 경우-속성이 옆에 있는 경우");
//		 System.out.println("tableText : "+tableText);
		 String[] tempText = tableText.split("\n", -1);
		 String qual="";
		 for (int i = 0; i < tempText.length; i++) {
			 qual+= "-"+tempText[i];
		 }
		 
		 listData.setCom_qual(listData.getCom_qual()+qual);
		 System.out.println("자격요건 : "+qual);
	}
	 @BeforeClass
	public void setUp() throws Exception {
		 System.setProperty("webdriver.chrome.driver", path+"/crawl/"+"chromedriver.exe"); //ũ�� ����̹� ���� ��μ���
	        driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //����ð� 5�ʼ���
	        driver.get("https://www.saramin.co.kr/zf_user/auth?url=%2Fzf_user%2F");  //������ ����Ʈ
	}
	@Test
	public void Step_01_login_Test () throws Exception {
		driver.findElement(By.name("id")).sendKeys("chlgudrbdn");  //ID
	    driver.findElement(By.name("password")).sendKeys("m6529194!"); //���
	    driver.findElement(By.className("btn-login")).click(); //�α��� ��ư Ŭ��
	}	 
	@Test
	public void Step_02_scraping() throws Exception {
		driver.get("http://www.saramin.co.kr/zf_user/member/suited-recruit-mail/list");  //������ ����Ʈ
	    WebElement tempList = driver.findElement(By.id("list_detail")); //ä�� ��� ������
	    List<WebElement> list = tempList.findElements(By.className("inner"));      //����� ����Ʈ�� �־��
	    List<String> listOfRecruitLink = new ArrayList(); 
	    for(WebElement inner : list ) {
	    	List<WebElement> companyInfo=inner.findElements(By.className("company_name"));
	        for(WebElement info  : companyInfo) {
//	        System.out.println("info.getText()  : "+info.getText() );
	        WebElement javascript_link= info.findElement(By.tagName("a"));
//	        System.out.println("javascript_link : "+javascript_link.getAttribute("href"));
	        String Link= show_detail_contents( javascript_link.getAttribute("href"));
//	        System.out.println("Link : "+Link);
	        listOfRecruitLink.add(Link);
	        }
	    }
//		String[] noticeKeyword_preferred = {"우대", "자격", "조건"", 지원"지원", "우대", "자격", "조건"};
//	    Arrays.sort(noticeKeyword_preferred);
//	    System.out.println("noticeKeyword_preferred : "+Arrays.toString(noticeKeyword_preferred));
	    
	    //parsing
	    int cnt = 0;
	    int cntNotDone=0;
	    for(String link : listOfRecruitLink) {
	    	cnt++;
	    	System.out.println("lets parse no"+cnt+". : \n"+"http://www.saramin.co.kr"+link);
	    	driver.get("http://www.saramin.co.kr"+link);
	        WebElement detail_user_content = driver.findElement(By.className("detail_user_content")); //일단 이것부터 찾는다. 대부분 근본이 이쪽을 따른다.
	        
	        
	        try {
//	        	if( !detail_user_content.findElements(By.tagName("recruit_guideline_preferred")).isEmpty() ){//case1 'recruit_guideline_preferred' 태그가 있음.
	        	if( !detail_user_content.findElements(By.tagName("recruit_guideline_preferred")).isEmpty() ){//case1 'recruit_guideline_preferred' 태그가 있음.
		        	String targetText = detail_user_content.findElement(By.tagName("recruit_guideline_preferred")).getText();//이걸 찾는게 제일 빠르다.
		        	categorizer_with_recruit_guideline_preferred(targetText);
	        	}
	        	else if( !detail_user_content.findElements(By.className(("table_summary" ))).isEmpty()  ) {//일단 recruit_guideline_preferred는 없어도 detail_user_content에 th 태그가 있음(최소한의 구조를 갖춘 표가 있는경우).
	        		System.out.println("table_summary");
	        		String tableText = detail_user_content.findElement(By.className(("table_summary" ))).getText(); // 그중에서도 table_summary가 있는 경우.
	    	        categorizer_with_recruit_guideline_preferred(tableText);//recruit_guideline_preferred 태그만 없을 뿐 외견상 거의 같은 형식이다.
	        	}
	        	else if( !detail_user_content.findElements(By.className(("temptb" ))).isEmpty()  ) {//일단 recruit_guideline_preferred는 없어도 detail_user_content에 th 태그가 있음(최소한의 구조를 갖춘 표가 있는경우).
	        		System.out.println("temptb");// 그중에서도 temptb가 있는 경우. 
	        		categorizer_only_table(detail_user_content.findElement(By.className(("temptb" ))));//보통 배경이 녹색이다. 아마 긁어오는 것으로 보임.
	        	}
	        	else if(!detail_user_content.findElements(By.tagName("table")).isEmpty() ) {//
	        		System.out.println("예외 : 그나마 참조할만한 테이블이 테이블 안에 있는 경우.");
//	        		List<WebElement> tables = detail_user_content.findElements(By.tagName("table")); //보통 section아래 두겹에서 세겹의 table이 되어 있다.
	        		WebElement table = detail_user_content.findElement(By.tagName("table")); //보통 section아래 두겹에서 세겹의 table이 되어 있다.
	        		
	        		while(! table.findElements(By.tagName("table")).isEmpty()) { //내부에 가장 손자 table이 나올 때까지 파고 들어간다.
	        			table=table.findElement(By.tagName("table"));
	        		}
	        		categorizer_only_table(table);
	        	}
	        	else if(!detail_user_content.findElements(By.tagName("img")).isEmpty()) {//이미지는 일단 구현 후순위에 둔다.
	        		cntNotDone++;//parsing 실패했으므로.
	        		System.out.println("예외 : 이미지는 현재 거의 parsing이 불가능."+cntNotDone);
	        	}
	        	else if( detail_user_content.findElements(By.tagName("table")).isEmpty() ) {//이미지도 쓸만한 태그도 테이블도 없는 경우
	        		List<WebElement> detail_meta_list = driver.findElements(By.className("detail_meta"));//detail_meta클래스 중에 관련 정보 찾는다.
	        		for(WebElement detail_meta : detail_meta_list) {
//	        			System.out.println(detail_meta.getText());
	        			if( detail_meta.getText().contains("자격")
	        					||detail_meta.getText().contains("요건") ) {//h3에 아마 이 단어 둘중 하나는 있을 것이다.
	        				categorizer_only_detail_meta( detail_meta.findElement(By.className("tbl_meta")).getText() );
	        			}
	        		}
	        	}
	        	else {
	        		cntNotDone++;
	        		System.out.println("그외의 예외 사항 : \n"+cntNotDone/*+"http://www.saramin.co.kr"+link*/);
	        	}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	        
	      }
	}

	@AfterClass
	 public static void tearDown() throws Exception {
	      driver.quit();
	 }
	 
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CrawlAddAction");
	   	ActionForward forward=new ActionForward();

	   	try{
			HttpSession session = request.getSession();
			path = session.getServletContext().getRealPath("/");
		    System.out.println("■path:::"+path+"crawl\\chromedriver.exe");
		    setUp();
		    Step_01_login_Test ();
		    Step_02_scraping();
		    System.out.println("OK. done.");
		    tearDown();
	    
	//	    CrawlDAOImpl.(boarddata);
	//   		result=boarddao.boardInsert(boarddata);
   		
   		
	   		/*if(result==false){
	   			System.out.println("게시?�� ?���? ?��?��");
	   			return null;
	   		}
	   		System.out.println("게시?�� ?���? ?���?");*/
		    
	   		forward.setRedirect(true);
	   		forward.setPath("./CrawlListAction.bo");
	   		return forward;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}