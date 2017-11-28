package net.crawl.action;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
 
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import net.crawl.db.CrawlDAOImpl;
import net.crawl.db.search_re_com_list_Bean;
 
@FixMethodOrder (MethodSorters.NAME_ASCENDING)

public class saraminTest {
    private static WebDriver driver;
    String Title = null;
    String URL = null;
    String alertText = "";

	CrawlDAOImpl boarddao=new CrawlDAOImpl();
	search_re_com_list_Bean crawlData=new search_re_com_list_Bean();
   	
    public String show_detail_contents(String javascript_link) {
    	String link;
    	String temp = javascript_link.substring( javascript_link.indexOf("(")+1 ,  javascript_link.indexOf(")")-1);
    	temp=temp.replace("'", "");
    	temp=temp.replace(" ", "");
//    	System.out.println(temp);

    	String[] params=temp.split(",", -1);
    	// params[0]=rec_idx, params[1]=recommend_ids, params[2]=t_content, params[3]=list_seq, params[4]=last_param
//    	for(String s : params) System.out.println(s);// for check
    	if(params[4].equals("null")) {
    		params[1]="none";
    	}
    	link="/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
    				+params[3]+"&inner_term="+params[2]+"&view_type=tailor&rec_idx="+params[0]+"&recommend_ids="+params[1]+"&t_ref=suited_list&t_ref_content="+params[2];
/* origianl js function
//			function show_detail_contents(rec_idx, recommend_ids, t_content, list_seq, last_param)
//        {
//            var openNewWindow = window.open();
//            if (last_param == 'none')
//            		recommend_ids = "none";
//            openNewWindow.location.href = "/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
//    			+list_seq+"&inner_term="+t_content+"&view_type=tailor&rec_idx="+rec_idx+"&recommend_ids="+recommend_ids+"&t_ref=suited_list&t_ref_content="+t_content;
//        }
    	System.out.println(link);//check*/
    	return link;
    }
    public String removeSpace(String targetText) {//공백제거용.
    	targetText=targetText.replace(" ", "");
    	return targetText;
    }
    public void categorizer_with_recruit_guideline_preferred(String targetText) {
		String preex="";
		String qual="";
		
		String[] tempText=targetText.split("\n",-1); //일단 문자열을 줄 별로 나눈다. 빈곳도 일단은 나타낸다.
		String regex1 = "^[가-힣]*$"; //check category
		String regex2 = "^- .*$"; //check item

		for(int i =0 ; i<tempText.length ; i++) {
			String s = tempText[i];
			System.out.println("itme : "+s);
			
			
			if(s.matches(regex1)) {
				
				if(s.contains("우대")) {
//					System.out.print("우대이하 파트를 읽는다! \n");
					i++;
					s = tempText[i];
					while(s.matches(regex2)) {
						System.out.println("string : "+s);
						preex += s;
						i++;
						if(i<tempText.length) {
							s = tempText[i];
						}else {
							break;
						}
					}
					removeSpace(preex);
					System.out.println("우대사항: "+preex);
					crawlData.setCom_preex(crawlData.getCom_preex() + preex );
				}
				
				if(s.contains("자격")) {
//					System.out.print("자격이다! \n");
					i++;
					s = tempText[i];
					while(s.matches(regex2)) {
						System.out.println("string : "+s);
						qual += s;
						i++;
						if(i<tempText.length) {
							s = tempText[i];
						}else {
							break;
						}
					}
					removeSpace(qual);
					System.out.println("자격요건: "+qual);
					crawlData.setCom_qual( qual );
				}
				
				if(s.contains("필수")) {
//					System.out.print("필수다! \n");
					i++;
					s = tempText[i];
					while(s.matches(regex2)) {
						System.out.println("string : "+s);
						qual += s;
						i++;
						if(i<tempText.length) {
							s = tempText[i];
						}else {
							break;
						}
					}
					removeSpace(qual);
					System.out.println("필수요건: "+qual);
					crawlData.setCom_qual( qual );
				}
			}
			else if(s.isEmpty()) { //check enter //equals==""나 "\n"나 "\\"론 안됨.
//				System.out.println("이것은 공백");
				continue;
			}
		}
    }
    
    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); //ũ�� ����̹� ���� ��μ���
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
//        	else if(table.findElement(By.cssSelector(".detail_user_content table th")).getText().contains("지원") || 
//        			table.findElement(By.cssSelector(".detail_user_content table th")).getText().contains("자격") || 
//        			table.findElement(By.cssSelector(".detail_user_content table th")).getText().contains("우대") || 
//        			table.findElement(By.cssSelector(".detail_user_content table th")).getText().contains("조건")) {
//        		
//        		
//        	}
//        	else if(  table.findElement(By.cssSelector("detail_meta > h3")).getText().contains("지원") ==null  ) {//대신 별도의 표로 지원자격이란 detail meta란 div하위 h3 class="title_meta" 지원자격/우대조건인 경우.
//
//    			
//    		}
//        	else if(   ) {//이 마저도 없는 이미지 인식은 어려우니 패스하되 표시만 해두자. section에 detail_user_content란 클래스로 명명된 부분에 이미지가 있으면 그런 케이스.
//    			System.out.println("image!");
//    		}
        	
        	
        	
//    		//여러칸이라면 모집분야별로 어떻게 할지 고민해봐야함.
//    		//근데 아마 사용자의 채용 요건이 명확하지 않다면 이부분은 사실 건들기가 어렵고, 사람인 자체의 검색엔진 문제이기 때문에 그냥 무시하는것이 상책.
//    		//tit_section above_table_summary에 (th기준으로 가장 첫번째)
//    		if(recruit_guideline_preferred == null) {//case1 'recruit_guideline_preferred' 태그가 없음. 대신 별도의 표로 지원자격이란 detail meta란 div하위 h3 class="title_meta" 지원자격/우대조건인 경우.
//    			
////    			<div class="detail_meta"><h3 class="title_meta">지원자격</h3><table class="tbl_meta"><colgroup><col style="width:110px;"><col style="width:330px;"><col style="width:110px;"><col style="width:330px;"></colgroup><tbody><tr><th scope="row">경력</th><td colspan="3">신입</td></tr><tr><th scope="row">학력</th><td colspan="3">학력무관										, 교육수강자 및 개발 가능자</td></tr></tbody></table></div>
//    		}
//    		if(recruit_guideline_preferred == null) {//case2 'recruit_guideline_preferred' 태그가 없음. 대신 형식은 맞춘 똑같은 표가 존재.
////    			<table class="table_summary" cellspacing="0" cellpadding="0"><colgroup><col width="15%"><col width="30%"><col></colgroup><thead><tr><th scope="col">모집분야</th><th scope="col">담당업무</th><th scope="col">자격요건 및 우대사항</th></tr></thead><tbody><tr><td><p>&nbsp;</p><p>(연구원, 사원, 팀원급)</p></td><td class="left_align">
////                SI/SM 사업본부 6팀                </td><td class="left_align"><p><b>자격요건</b></p><p>- 학력 : 대졸 이상 (4년)</p><p>- 경력 : 신입 </p><p>- 성별 : 무관</p><p>- 모집인원 : 00명</p><br><p><b>필수사항</b></p><p>- 자연계열</p><p>- 전기/전자공학</p><p>- 컴퓨터/시스템공학</p><p>- 공학계열</p><br><p><b>우대사항</b></p><p>- 석사학위 수여자</p><p>- 해당직무 근무경험</p><p>- 인근거주자</p><p>- 문서작성 우수자</p><p>- 정보처리기사</p></td></tr></tbody></table>
//    		}
//    		if(recruit_guideline_preferred == null) {//case3 'recruit_guideline_preferred' 태그가 없음. 형식도 다르고 전혀 다른 데서 긁어온 표.
//    		}
        }

        	 
//         
//        for(String Handle : driver.getWindowHandles())  //��â���� ����Ī
//            driver.switchTo().window(Handle);
//        driver.findElement(By.xpath("//*[@id='main_top_2']/div[1]/fieldset[1]/div/a/img")).click();//�����ϱ� Ŭ��
//         
//        driver.switchTo().frame("editor");  //�����ϱ� �Է�â iframe���� ����Ī
//         
//        driver.findElement(By.xpath("//*[@id='title']")).sendKeys("����������"); //�����Է�
//        driver.switchTo().frame("SmartEditorIframe");                       //�����Է� â iframe ����Ī
//        driver.findElement(By.cssSelector("body")).sendKeys("���̻��");        //�����Է�
//         
//        driver.switchTo().defaultContent();                                 //����Ī �ʱ�ȭ
//        //�ٽ� �����ϱ� â iframe ����Ī
//        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
//         
//         
//         
//        driver.findElement(By.xpath("//*[@id='au_submit_button']/div[2]/a[1]/img")).click();  //�ӽ����� Ŭ��
//         
//        String num = driver.findElement(By.id("temporary_save_count")).getText();   //���� �ӽ����� �� �� ī��Ʈ ����
//         
//        Thread.sleep(1000);     //�ʹ� ���� �� â ������ �ȵǴ� �� �����ϱ� ���� ���� �߰�
//         
//        Alert alert = driver.switchTo().alert();            //�� â���� ����Ī
//        alertText = alert.getText();                        //�� â �޽��� ����
//        alert.accept();                                     //�� â Ȯ�� Ŭ��
//        System.out.println(alertText);                      //��� â �޽��� ���
//         
//        Thread.sleep(1000);
//         
//        System.out.println("�ӽ����� �� ���� : "+num);
//         
//        driver.findElement(By.xpath("//*[@id='title']")).clear();       //���� ���� ����
//        driver.findElement(By.xpath("//*[@id='title']")).sendKeys("���̺�Ľ����쿡��"); //���� �ٽ� �Է�
//                 
//         
//         
//        driver.switchTo().frame("SmartEditorIframe");                   //���� �Է� â iframe ����Ī
//        driver.findElement(By.cssSelector("body")).sendKeys("����ĥ��");    //�����Է�
//        driver.switchTo().defaultContent();                             //â ����Ī �ʱ�ȭ
//        //�ٽ� �����ϱ� â iframe ����Ī
//        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
//         
//        Thread.sleep(1000);
//         
 
    }
     
    @Test
    public void Step_03_counting_Test () throws Exception {
    	
//        driver.findElement(By.xpath("//img[@alt='�����ܰ�']")).click(); //�����ܰ� ��ư Ŭ��
//         
//        //ī�װ�
//        Thread.sleep(1000);
//        driver.findElement(By.linkText("���丮 ���� ����")).click();  //���丮 �������� Ŭ��
//         
//         
//        Select dropdown = new Select(driver.findElement(By.id("choose_dir_d1")));  //ù��° ���� �ڽ��� ������
//        dropdown.deselectAll();                 //���� ��� �ʱ�ȭ
//        dropdown.selectByValue("1");            //value 1�� ���� ����
//         
//         
//        dropdown = new Select(driver.findElement(By.id("choose_dir_d2")));      //�ι�° �����ڽ� ������
//        dropdown.deselectAll();                                                 //���� ����
//        dropdown.selectByValue("101");     
//         
//        dropdown = new Select(driver.findElement(By.id("choose_dir_d3")));
//        dropdown.deselectAll();
//        dropdown.selectByValue("10103");
//         
//        dropdown = new Select(driver.findElement(By.id("choose_dir_d4")));
//        dropdown.deselectAll();
//        dropdown.selectByValue("1010303");
//         
//        Thread.sleep(1000);
//         
//        driver.switchTo().defaultContent();             //â ����Ī �ʱ�ȭ
//        //�����ϱ� â iframe ����Ī
//        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
//        driver.findElement(By.xpath("//*[@id='au_submit_button2']/div[2]/a[1]/img")).click();//�ӽ����� Ŭ��
//         
//        Thread.sleep(3000);
//         
//        Alert alert = driver.switchTo().alert();        //�� â ����Ī
//        alertText = alert.getText();
//        alert.accept();                                 //�� Ȯ�� Ŭ��
//        System.out.println("�ٸ��������� "+alertText);
//         
//        String num = driver.findElement(By.id("temporary_save_count")).getText();  //�ӽ����� ī��Ʈ ����
//        System.out.println("�ӽ����� �� ���� : "+num);
//         
//         
//         
 
    }
     
    @Test
    public void Step_04_analyze_Test () throws Exception {
//         
//        // �ӽ����� â
//        driver.findElement(By.className("_tempsave_open_close")).click();   //�ӽ����� �� Ŭ��
// 
//        driver.switchTo().defaultContent();                                 //â �ʱ�ȭ
//        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']"))); //�����ϱ� iframe â ����Ī
// 
//        String info = driver.findElement(By.className("info_tempsave")).getText(); //�ӽ����� â �ȳ� �� ����
//        System.out.println("�ӽ����� â " + info);
// 
//        WebElement tempList = driver.findElement(By.className("tempsave_list")); //�ӽ����� �� ��� ������
//        List<WebElement> list = tempList.findElements(By.className("q_on"));      //����� ����Ʈ�� �־��
// 
//        //���� ���̴ϱ� 2��°�� 1�� ��� ���� ��ư�� Ŭ��
//        list.get(1).findElement(By.tagName("img")).click();
//        Thread.sleep(3000);
//        Alert alert = driver.switchTo().alert();            //���� Ȯ�� �� â
//        alertText = alert.getText();
//        alert.accept();                                     //Ȯ�� Ŭ��
//        System.out.println("����" + alertText);
// 
         
         
    }
     
    @Test
    public void Step_05_out_Test () throws Exception {
        /*******���� �� ������ ����*******/
//        driver.switchTo().defaultContent();                            
//        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='editor']")));
// 
//        WebElement tempList1 = driver.findElement(By.className("tempsave_list"));
//        List<WebElement> list1 = tempList1.findElements(By.className("q_on"));
// 
//        //���⼭ ����� ������ ���� 1��°�� 0��° ��� ������ư Ŭ��
//        list1.get(0).findElement(By.tagName("img")).click();   
//        Thread.sleep(3000);
//        Alert alert = driver.switchTo().alert();
//        alertText = alert.getText();
//        alert.accept();
//        System.out.println(alertText);
// 
//        Thread.sleep(1000);
// 
//        String num = driver.findElement(By.id("temporary_save_count")).getText();
//        System.out.println("�ӽ����� �� ���� : " + num);
// 
//        Thread.sleep(1000);
         
         
    }
     
     
 
//    @AfterClass
//    public static void tearDown() throws Exception {
//        driver.quit();
//    }
 
}


//��ó: http://yonoo88.tistory.com/603 [yonoo's]