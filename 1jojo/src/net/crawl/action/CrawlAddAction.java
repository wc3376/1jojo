package net.crawl.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrawlAddAction implements Action {
	CrawlDAOImpl boarddao = new CrawlDAOImpl();
//	search_list_Bean listData = new search_list_Bean();
//	search_qual_Bean qualData = new search_qual_Bean();

	private static WebDriver driver;
	String Title = null;
	String URL = null;
	String alertText = "";
	static String path = "";
	
	public Boolean isContainQualOrPreex(String targetText) {
		String keywords[] = {"우대", "조건", "자격", "사항", "필수"};
		for (String s : keywords) {
			if(targetText.contains(s))
				return true;
		}
		return false;
	}
	
	public String show_detail_contents(String javascript_link) { // 살짝 문제가 있는데 ajax로 인해 다른 페이지 정보까지 몇개 끌고올수 있다.
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

	/*
	 * public String removeSpace(String targetText) {// 공백제거용. 생각해보니 쓸모 없음.
	 * targetText.replace(" ", ""); return targetText; }
	 */

	public search_list_Bean categorizer_with_recruit_guideline_preferred(search_list_Bean listData, String targetText) {
		String preex = "";
		String qual = "";

		String[] tempText = targetText.split("\n", -1); // 일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
		String regex1 = "^[가-힣]*$"; // check category
		String regex2 = "^- .*$"; // check item
		//포맷이 가장 잘 잡혀있어서 해당 정규식을 사용하면 무난하게 카테고라이징 가능.
		for (int i = 0; i < tempText.length; i++) {
			String s = tempText[i];
			// System.out.println("itme : " + s);
			if (s.matches(regex1)) {
				if (s.contains("우대")) {
					// System.out.print("우대이하 파트를 읽는다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
//						 System.out.println("string : " + s);
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
				}else if (s.contains("자격")) {
					// System.out.print("자격이다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
						// System.out.println("string : " + s);
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
				}else if (s.contains("필수")) {
					// System.out.print("필수다! \n");
					i++;
					s = tempText[i];
					while (s.matches(regex2)) {
						// System.out.println("string : " + s);
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
		return listData;
	}

	private search_list_Bean categorizer_only_String(search_list_Bean listData, String tableText) {//이미 자격요건들이 포함된 해당 열의 문자열. 아마도 테이블인 주제에 칸이 하나뿐인 경우에도 유효할 것으로 보임.
		System.out.println("정형화되지 못한 테이블에서 주어진 문구를 카테고리화");
		System.out.println("------tableText start : ------\n" + tableText + "\n------table Text end------");
		String preex = "";
		String qual = "";

		String[] tempText = tableText.split("\n", -1); // 일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
		// String regex1 = "^[가-힣]*$"; // check category //일단 아무 표시도 없이 쓰는 경우도 있고 하다.
		String regex2 = "^- .*$"; // 보통 - 뒤에 무슨무슨 요건을 적는게 보통이다. 그외의 특수문자는 신경쓰지 말자.

		for (int i = 0; i < tempText.length; i++) {//한줄씩 분석.
			String s = tempText[i];
			// System.out.println("itme : " + s);
			// if (s.matches(regex1)) {
			if (s.contains("우대")) {
				i++;
				s = tempText[i];
//				System.out.println("우대이하 파트를 읽는다! ="+s);
				while (s.matches(regex2) ) {// - 형식을 가진 동시에 우대사항, 자격 같은 요상한게 붙지 않은 케이스.
//					 System.out.println("string : " + s);
					preex += s;
					i++;
					if (i < tempText.length) {
						s = tempText[i];
					} else {
						break;
					}
				}
				System.out.println("우대사항: " + preex);
				if(listData.getCom_preex() ==null) {
					listData.setCom_preex("");
				}
				listData.setCom_preex(listData.getCom_preex() + preex);
			}else if (s.contains("자격") || s.contains("필수")) {
				// System.out.print("자격이다! \n");
				i++;
				s = tempText[i];
				while (s.matches(regex2)  && !isContainQualOrPreex(s) ) {
					// System.out.println("string : " + s);
					qual += s;
					i++;
					if (i < tempText.length) {
						s = tempText[i];
					} else {
						break;
					}
				}
				if(listData.getCom_qual() ==null) {
					listData.setCom_qual("");
				}
				System.out.println("자격요건 또는 필수요건: " + listData.getCom_qual()+qual);
				listData.setCom_qual(listData.getCom_qual()+qual);
			} else {//어지간해선 저 둘중 하나에 걸리면 끝까지 갈 것이다. 아마 빈칸 같은 경우를 얘기할 듯.
				continue;
			}
		}
		System.out.println("자격요건 : " + listData.getCom_qual());
		System.out.println("우대요건 : " + listData.getCom_preex());
		return listData;
	}

	private search_list_Bean categorizer_only_table(WebElement table, search_list_Bean listData) {
		System.out.println("------tableText start : ------\n" + table.getText() + "\n------table Text end------");
//		if (isContainQualOrPreex(table.findElement(By.tagName("tr")).getText()) ) {// 행에 자격 또는 요건이란 단어가 있는 tr을 찾는다.
			ArrayList<WebElement> tr_list = (ArrayList<WebElement>) table.findElements(By.tagName("tr"));
			int colNo = 0; // 현재 읽고 있는 열의 숫자. 행의 숫자는 필요 없음.
			
			ArrayList<Integer> colNo_with_qual = new ArrayList<Integer>(); // 자격 요건이란 정보가 담긴 열의 숫자 리스트. 보통 하나지만 우대사항과 나눠진 경우까지 감안.
			boolean isThead = true;//당연히 첫줄은 th인 항목이 들어 있을 것이다.
			HashMap<Integer, String> category = new HashMap<Integer, String>();//속성, 값
			for (WebElement tr : tr_list) {//행
				ArrayList<WebElement> td_list = (ArrayList<WebElement>)tr.findElements(By.tagName("td"));//열
				colNo = 0; // 초기화
				for (WebElement td : td_list) {
					System.out.println("colNo=" + colNo + "/"+td_list.size()+ ", colNo_with_qual=" + colNo_with_qual + ", td.getText()" + td.getText());//check
					if (isThead == true) {
						if ( isContainQualOrPreex(td.getText()) ) { //자격요건과 같은 문자열이 존재.
							colNo_with_qual.add(colNo);//하는 열의 번호 추가.
							category.put(colNo, td.getText());//임시로 속성에 위치한 자격요건이나 우대조건과 같은 글자를 담을 변수
						}
						++colNo;
					}else {//
						if (colNo_with_qual.contains(colNo) ) {
							String headAndContent=category.get(colNo)+"\n"+td.getText();
							listData = categorizer_only_String(listData, headAndContent );
						}
						++colNo;
					}
					isThead = false;//더이상 속성은 없을 것이다. 유의미한건 첫번째 tr 때 뿐.
				}
			}
			return listData;
	}

	private search_list_Bean categorizer_only_detail_meta(search_list_Bean listData, WebElement detail_meta) {
		System.out.println("detail_meta만 있는 경우-속성이 옆에 있는 경우");
		// System.out.println("tableText : "+tableText);
//		if(!detail_meta.findElement(By.className("tbl_meta")).isDisplayed()) {
//			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		}
		String qualOrPreex = detail_meta.findElement(By.tagName("h3")).getText()+"\n";
		WebElement table = detail_meta.findElement(By.className("tbl_meta"));
//		String[] tempText = table.getText().split("\n", -1);
		ArrayList<WebElement> tr_list = (ArrayList<WebElement>) table.findElements(By.tagName("tr"));
		for (WebElement tr : tr_list) {//행
			qualOrPreex += "- "+tr.findElement(By.tagName("th")).getText()+" : "+tr.findElement(By.tagName("td")).getText();
		}
//		for (int i = 0; i < tempText.length; i++) {
//			qualOrPreex += "- " + tempText[i];
//		}
		listData = categorizer_only_String(listData, qualOrPreex);
//		System.out.println("자격요건 : " + listData.getCom_qual());
//		System.out.println("우대요건 : " + listData.getCom_preex());
		return listData;
	}

	@BeforeClass
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", path + "/crawl/" + "chromedriver.exe"); // ũ�� ����̹� ���� ��μ���
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // ����ð� 5�ʼ���
		driver.get("https://www.saramin.co.kr/zf_user/auth?url=%2Fzf_user%2F"); // ������ ����Ʈ
	}

	@Test
	public void Step_01_login_Test() throws Exception {
		driver.findElement(By.name("id")).sendKeys("chlgudrbdn"); // ID
		driver.findElement(By.name("password")).sendKeys("m6529194!"); // ���
		driver.findElement(By.className("btn-login")).click(); // �α��� ��ư Ŭ��
	}

	@Test
	public List<search_list_Bean> Step_02_scraping() throws Exception {
		driver.get("http://www.saramin.co.kr/zf_user/member/suited-recruit-mail/list"); // ������ ����Ʈ
		WebElement tempList = driver.findElement(By.id("list_detail"));
		List<WebElement> list = tempList.findElements(By.className("inner")); // 채용공고 element 리스트팅.
		List<String> listOfRecruitLink = new ArrayList(); // 채용공고 링크 리스트 모음.
		ArrayList<String> listOfRecruitcom = new ArrayList(); // 채용공고 링크 회사명모음.
		List<search_list_Bean> listOfResult= new ArrayList(); // 채용공고 링크 리스트 모음.
		
		for (WebElement inner : list) {
			List<WebElement> companyInfo = inner.findElements(By.className("company_name"));
			for (WebElement info : companyInfo) {
				WebElement javascript_link = info.findElement(By.tagName("a"));//링크 얻어올 것.
				listOfRecruitcom.add( info.getText() );
				String Link = show_detail_contents(javascript_link.getAttribute("href"));
				listOfRecruitLink.add(Link);
			}
		}
		// parsing
		int cnt = 0;
		int cntNotDone = 0;
		
		for (String link : listOfRecruitLink) {
			search_list_Bean listData = new search_list_Bean(); //최초 raw 데이터가 나올 화면에 나올 항목들이자, 분석에 쓰일 최소 단위 기본 객체.
//			search_com_No;//번호
//			com_qual;//업체 지원자격
//			com_preex;//업체 우대사항
//			com_name;//지원자격포함 기업명
//			com_link;//지원자격포함 기업링크
//			listData.setSearch_com_No(  );//일단 사용자당 하나만 부여할까 생각중이다. 생각 같아선 DB연동해서 다른건 싹다 지워버릴 생각이지만 //걍DB에서 알아서 serial 부여하도록 함. 
			listData.setCom_name( listOfRecruitcom.get(cnt) );//위에서 링크 읽어둘 때 미리 킵해둔 회사명도 여기에서 입력해둔다.
			listData.setCom_link(link);
//			com_qual; com_preex;은 아래 과정을 거치면서 입력될 것이다.
			
			cnt++;
			System.out.println("lets parse no" + cnt + ". : \n" + "http://www.saramin.co.kr" + link);
			driver.get("http://www.saramin.co.kr" + link);
			WebElement detail_user_content = driver.findElement(By.className("detail_user_content")); // 일단 이것부터 찾는다. 대부분 근본이 이쪽을 따른다.

			try {
				if (!detail_user_content.findElements(By.tagName("recruit_guideline_preferred")).isEmpty()) {//case1 'recruit_guideline_preferred' 태그가 있음.
					String targetText = detail_user_content.findElement(By.tagName("recruit_guideline_preferred")).getText();// 이걸 찾는게 제일 빠르다.
					listData=categorizer_with_recruit_guideline_preferred(listData, targetText);
				} else if (!detail_user_content.findElements(By.className(("table_summary"))).isEmpty()) {// recruit_guideline_preferred는 없어도 detail_user_content에 th 태그가 있음(최소한의  구조를 갖춘 표가  있는경우).
					System.out.println("table_summary가 있는 경우");// 그중에서도  table_summary가  있는  경우. 제법 많이 나오는 패턴
					listData=categorizer_only_table( detail_user_content.findElement(By.className(("table_summary"))) ,  listData);
					// recruit_guideline_preferred 태그만 없을 뿐 외견상 거의 같은 형식이다.
				} else if (!detail_user_content.findElements(By.className(("temptb"))).isEmpty()) {// temptb가 있는 경우.
					System.out.println("temptb가 있는 경우");// 그중에서도 temptb가 있는 경우. 제법 많이 나오는 패턴
					listData=categorizer_only_table(detail_user_content.findElement(By.className(("temptb"))), listData);// 보통 이런건 배경이 녹색이다. 아마 긁어오는 것으로 보임.
				} else if (!detail_user_content.findElements(By.tagName("table")).isEmpty()) {//
					System.out.println("그나마 참조할만한 테이블이 테이블 안에 있는 경우.");
					WebElement table = detail_user_content.findElement(By.tagName("table")); // 보통 section아래 두겹에서 세겹의  table이 되어 있다.
					while (!table.findElements(By.tagName("table")).isEmpty()) { // 고로 내부에 가장 손자 table이 나올 때까지 파고 들어간다.
						table = table.findElement(By.tagName("table"));
					}
					listData=categorizer_only_table(table, listData);
				} else if (!detail_user_content.findElements(By.tagName("img")).isEmpty()) {// 이미지는 일단 구현 후순위에 둔다.
					cntNotDone++;// parsing 실패했으므로.
					System.out.println("예외 : 이미지는 현재 거의 parsing이 불가능." + cntNotDone);
				} else if (detail_user_content.findElements(By.tagName("table")).isEmpty()) {// 이미지도 쓸만한 태그도 테이블도 없는 경우
					WebElement view_content__shadow_section_relay__first = driver.findElement(By.cssSelector("div.first"));
					ArrayList<WebElement> detail_meta_list = (ArrayList<WebElement>) view_content__shadow_section_relay__first.findElements(By.className("detail_meta"));// detail_meta클래스 중에 관련 정보 찾는다.
					for (WebElement detail_meta : detail_meta_list) {
//						System.out.println("-------detail_meta.getText()-------\n"+detail_meta.getText()+"\n-------end-------");
						if (isContainQualOrPreex(detail_meta.getText()) && !detail_meta.findElements(By.className("tbl_meta")).isEmpty()) {// h3에 아마 자격 , 우대 요건 같은 단어 둘중 하나는 있을 것이다.
							listData = categorizer_only_detail_meta( listData, detail_meta );
						}
					}
				} else {
					cntNotDone++;
					System.out.println("그외의 예외 사항 : \n" + cntNotDone/* +"http://www.saramin.co.kr"+link */);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			listOfResult.add(listData);
			System.out.println("well crawled ratio = "+ (double)(listOfRecruitLink.size()-cntNotDone)/listOfRecruitLink.size());// scrap된 페이지 비율.
		}
		return listOfResult;
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CrawlAddAction");
		ActionForward forward = new ActionForward();
		CrawlDAOImpl crawldao=new CrawlDAOImpl();

		try {
			HttpSession session = request.getSession();
			path = session.getServletContext().getRealPath("/");
			System.out.println("■getRealPath:::" + path + "crawl\\chromedriver.exe");
			setUp();
			Step_01_login_Test();
			
			List<search_list_Bean> listOfResult= new ArrayList();
			List<search_list_Bean> listOfResult_at_DB= new ArrayList();
			listOfResult=Step_02_scraping();
			tearDown();
			System.out.println("OK. done.");
			// CrawlDAOImpl.(boarddata);
			for(search_list_Bean listData : listOfResult) {
				crawldao.search_list_Insert(listData);
			}
			/* if(result==false){ System.out.println("게시?�� ?���? ?��?��"); return null; }
			 * System.out.println("게시?�� ?���? ?���?");*/
			int search_com_No = listOfResult.get(0).getSearch_com_No();
			request.setAttribute("search_list_count", listOfResult.size()); //검색 결과 수
			listOfResult_at_DB = crawldao.getSearch_list( search_com_No );//DB에 놓음 검색결과 읽어온다.
			request.setAttribute("search_list", listOfResult_at_DB); //검색결과 리스트를 세션으로 전송
			request.setAttribute("search_com_No", search_com_No ); //search_com_No을 세션으로 전송
			

			

			forward.setRedirect(true);
			forward.setPath("/cwl_result.cr");
			return forward;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("문제가 생겼다. alert!");
		}
		return null;
	}
}