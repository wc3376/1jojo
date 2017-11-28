package net.crawl.action;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import net.board.action.Action;
import net.board.action.ActionForward;
 
@FixMethodOrder (MethodSorters.NAME_ASCENDING)
 
public class saraminTest implements Action{
    private static WebDriver driver;
    String Title = null;
    String URL = null;
    String alertText = "";
    
    public String show_detail_contents(String javascript_link) {
//      javascript: show_detail_contents('32329495', '', 'suited_recruit_01', 2, 'none') �̷��� ��ũ�� ����.
//      �Ʒ��� �Լ��� �ش� ������ �� �� �Ʒ� ��ũ�� ���� ���� �ȴ�.
    	String link;
    	String temp = javascript_link.substring( javascript_link.indexOf("(")+1 ,  javascript_link.indexOf(")")-1);
    	temp=temp.replace("'", "");// ���� ����ǥ ����
    	temp=temp.replace(" ", "");// ������� ����
    	System.out.println(temp);
    	
    	String[] params=temp.split(",", -1);
    	// params[0]=rec_idx, params[1]=recommend_ids, params[2]=t_content, params[3]=list_seq, params[4]=last_param
//    	for(String s : params) System.out.println(s);
    	if(params[4].equals("null")) {
    		params[1]="none";
    	}
    	link="/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
    			+params[3]+"&inner_term="+params[2]+"&view_type=tailor&rec_idx="+params[0]+"&recommend_ids="+params[1]+"&t_ref=suited_list&t_ref_content="+params[2];
// ���� ���� �ڹٽ�ũ��Ʈ �ڵ�
//			function show_detail_contents(rec_idx, recommend_ids, t_content, list_seq, last_param)
//        {
//            var openNewWindow = window.open();
//            if (last_param == 'none')
//            		recommend_ids = "none";
//            openNewWindow.location.href = "/zf_user/jobs/relay/recruit-view?inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_"
//    			+list_seq+"&inner_term="+t_content+"&view_type=tailor&rec_idx="+rec_idx+"&recommend_ids="+recommend_ids+"&t_ref=suited_list&t_ref_content="+t_content;
//        }
//    	http://www.saramin.co.kr/zf_user/jobs/relay/recruit-view?view_type=tailor&rec_idx=32346724&gz=1&recommend_ids=eJwzNjI2sjSxNAUAB1EBpg%3D%3D&inner_source=saramin&inner_medium=pattern&inner_campaign=suited_list_
//    			1&inner_term=suited_recruit_01&t_ref=suited_list&t_ref_content=suited_recruit_01#seq=0
//    	�� ������� �Ǿ�� �Ѵ�.
    	System.out.println(link);
    	return link;
    }
    
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	
    	
    	return null;
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
        		System.out.println("info.getText() ȸ���̸� : "+info.getText() );
        		WebElement javascript_link= info.findElement(By.tagName("a"));
//        		System.out.println("javascript_link : "+javascript_link.getAttribute("href"));
        		String Link= show_detail_contents( javascript_link.getAttribute("href"));
//        		System.out.println("Link : "+Link);
        		listOfRecruitLink.add(Link);
        	}
        }

        //���������� parsing�� �κ�.
        for(String link : listOfRecruitLink) {
//        	System.out.println("lets parse"+"http://www.saramin.co.kr/"+link);
        	driver.get("http://www.saramin.co.kr"+link); 
        	WebElement table = driver.findElement(By.className("table_summary")); 
        	System.out.println("it doen");
    		WebElement recruit_guideline_preferred= table.findElement(By.tagName("recruit_guideline_preferred"));
    		System.out.println("�ڰݿ�� �� ������ : "+ recruit_guideline_preferred.getText());
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