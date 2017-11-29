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

//	@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class CrawlAddAction implements Action {


	CrawlDAOImpl boarddao = new CrawlDAOImpl();
	search_re_com_list_Bean listData = new search_re_com_list_Bean();
	search_re_com_qual_Bean qualData = new search_re_com_qual_Bean();

	private static WebDriver driver;
	String Title = null;
	String URL = null;
	String alertText = "";

	public String show_detail_contents(String javascript_link) {
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

	public String removeSpace(String targetText) {// 공백제거용.
		targetText = targetText.replace(" ", "");
		return targetText;
	}

	public void categorizer_with_recruit_guideline_preferred(String targetText) {
		String preex = "";
		String qual = "";

		String[] tempText = targetText.split("\n", -1); // 일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
		String regex1 = "^[가-힣]*$"; // check category
		String regex2 = "^- .*$"; // check item

		for (int i = 0; i < tempText.length; i++) {
			String s = tempText[i];
			System.out.println("itme : " + s);

			if (s.matches(regex1)) {

				if (s.contains("우대")) {
					// System.out.print("우대이하 파트를 읽는다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
						System.out.println("string : " + s);
						preex += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					removeSpace(preex);
					System.out.println("우대사항: " + preex);
					listData.setCom_preex(listData.getCom_preex() + preex);
				}

				if (s.contains("자격")) {
					// System.out.print("자격이다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
						System.out.println("string : " + s);
						qual += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					removeSpace(qual);
					System.out.println("자격요건: " + qual);
					listData.setCom_qual(qual);
				}

				if (s.contains("필수")) {
					// System.out.print("필수다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
						System.out.println("string : " + s);
						qual += s;
						i++;
						if (i < tempText.length) {
							s = tempText[i];
						} else {
							break;
						}
					}
					removeSpace(qual);
					System.out.println("필수요건: " + qual);
					listData.setCom_qual(qual);
				}
			} else if (s.isEmpty()) { // check enter //equals==""나 "\n"나 "\\"론 안됨.
				// System.out.println("이것은 공백");
				continue;
			}
		}
	}
//	 @BeforeClass
	    public void setUp() throws Exception {
			//방법4. http://ohgyun.com/169
			
	    }
//	    @AfterClass
//	    public static void tearDown() throws Exception {
//	        driver.quit();
//	    }
	 
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CrawlAddAction");
//		방법1. http://mwultong.blogspot.com/2006/11/java-get-file-full-path-name.html
//	    File f = new File("chromedriver.exe");
//		try {
//		      System.out.println("f.getCanonicalPath()"+f.getCanonicalPath());
//		    } catch (IOException e) {
//		      System.err.println(e);
//		      System.exit(1);
//		    }
////		방법2. http://ggil.tistory.com/74
//		  System.out.println("request.getContextPath()="+ request.getContextPath() ); // 출력문 '/MvcLogic' 나의 웹어플리케이션 프로젝트 명 
//		  System.out.println( "request.getServletPath()="+ request.getServletPath()); // 출력문 '/Servlet/Controller' 앞에 웹플리케이션을 제외한 한 경로
		  /*
		   * 절대 경로를 구하는 메서드를 이용해서 request.getRealPath( "스트링" ); 메서드를 사용 했다
		   * 나의 생각은 "스트링"이 파일명과 일치 해서 해당 파일이 있는 절대 경로를 뽑아 내는 줄로 잘못 알고있었다..
		   * 하지만 실제로는 현제 웹어플리케이션 절대 경로 + 스트링 문자열 이다 !
		   * 현재 나의  웹어플리케이션의 절대 경로를 알고 싶을 경우   request.getRealPath( "" )  빈값을 주면 된다. 
		   */
//
//		saraminTest s = new saraminTest();
//		saraminTest.setUp();
		
		HttpSession session = request.getSession();
		String path = session.getServletContext().getRealPath("/");
	    System.out.println("■path:::"+path);
//		request.getSession().getServletContext().getRealPath("Chromewebdriver.exe");
	    System.out.println(" CrawlAddAction.class.getResource().getPath() "+CrawlAddAction.class.getResource("").getPath());
//		InputStream inStream = class.getClass().getClassLoader().getResourceAsStream("Web-INF/chromedriver.exe");		
		
		
	    
	    
	    
	    
	    
		
		System.setProperty("webdriver.chrome.driver", path+"chromedriver.exe"); //ũ�� ����̹� ���� ��μ���
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //����ð� 5�ʼ���
        driver.get("https://www.saramin.co.kr/zf_user/auth?url=%2Fzf_user%2F");  //������ ����Ʈ
//    }
//    @Test
//    public void Step_01_login_Test () throws Exception {
    	driver.findElement(By.name("id")).sendKeys("chlgudrbdn");  //ID
    	driver.findElement(By.name("password")).sendKeys("m6529194!"); //���
        driver.findElement(By.className("btn-login")).click(); //�α��� ��ư Ŭ��
//    }
//    @Test
//    public void Step_02_scraping() throws Exception {
        driver.get("http://www.saramin.co.kr/zf_user/member/suited-recruit-mail/list");  //������ ����Ʈ
        WebElement tempList = driver.findElement(By.id("list_detail")); //ä�� ��� ������
        List<WebElement> list = tempList.findElements(By.className("inner"));      //����� ����Ʈ�� �־��
        List<String> listOfRecruitLink = new ArrayList(); 
        for(WebElement inner : list ) {
        	List<WebElement> companyInfo=inner.findElements(By.className("company_name"));
        	for(WebElement info  : companyInfo) {
//        		System.out.println("info.getText()  : "+info.getText() );
        		WebElement javascript_link= info.findElement(By.tagName("a"));
//        		System.out.println("javascript_link : "+javascript_link.getAttribute("href"));
        		String Link= show_detail_contents( javascript_link.getAttribute("href"));
//        		System.out.println("Link : "+Link);
        		listOfRecruitLink.add(Link);
        	}
        }
        
//        String[] noticeKeyword_preferred = {"우대", "자격", "조건"", 지원"지원", "우대", "자격", "조건"};
//        Arrays.sort(noticeKeyword_preferred);
//        System.out.println("noticeKeyword_preferred : "+Arrays.toString(noticeKeyword_preferred));
        //parsing
        int cnt = 0;
        for(String link : listOfRecruitLink) {
        	cnt++;
        	System.out.println("lets parse no"+cnt+". : \n"+"http://www.saramin.co.kr"+link);
        	driver.get("http://www.saramin.co.kr"+link);
        	WebElement table = driver.findElement(By.className("table_summary")); //일단 이것부터 찾는다. 대부분 근본이 이쪽을 따른다.
        	if(table.findElement(By.tagName("recruit_guideline_preferred")).getText()  != null) {//case1 'recruit_guideline_preferred' 태그가 있음.
        		String targetText = table.findElement(By.tagName("recruit_guideline_preferred")).getText();//이걸 찾는게 제일 빠르다.
        		//그냥 section에 detail_user_content란 클래스로 명명된 부분 하위 table> thead > th태그 중에 자격|우대 라는 단어가 있는 표 긁어오는 걸로 퉁치기로함 // 
//        		System.out.println("recruit_guideline_preferred : \n"+ targetText );

        		System.out.println("Parsing Test"); 
        		categorizer_with_recruit_guideline_preferred(targetText);
        		
        		
        	}else {
        		continue;
        	}
        }
        
        
        
        
//		setUp();
		return null;
		
		
		
	}
}