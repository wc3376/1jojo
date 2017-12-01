package net.crawl.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CrawlDAOImpl {
	
	private SqlSession getSession() {
		SqlSession session=null;
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader("./SqlMapConfig.xml");
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(reader);
			session = sf.openSession(true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return session;
	}	
	
	
	/* 검색결과 일단 저장 */
	public void search_list_Insert(search_list_Bean search_list) {
		SqlSession session=null;
		int result = 0;		
		try{
			session = getSession(); 
			System.out.println("insert 1");
			
			System.out.println("search_com_No="+search_list.getSearch_com_No());//나올리가 없다.
			System.out.println("com_qual="+search_list.getCom_qual());
			System.out.println("com_preex="+search_list.getCom_preex());
			System.out.println("com_name="+search_list.getCom_name());
			System.out.println("com_link="+search_list.getCom_link());			
//			private int search_com_No;//번호
//			private String com_qual;//업체 지원자격
//			private String com_preex;//업체 우대사항
//			private String com_name;//지원자격포함 기업명
//			private String com_link;//지원자격포함 기업링크
			
			result=session.insert("crawl.crawl_insert", search_list);
			System.out.println("result="+result);
		}catch(Exception e){
			System.out.println("result="+result);
			System.out.println(e.getMessage());
		}		
	}	
	
	/* 게시판 총 게시물 수 */
	public int getListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("board.search_list_count")).intValue();	
		 
		return count;
	}
	
	/* 검색된 데이터 목록 */
	public List<search_list_Bean> getSearch_list(String search_com_No)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		List<search_list_Bean>  list = session.selectList("crawl.crawl_list", search_com_No);
	    return list;
	}	

	/* 게시판 조회수 증가 */
	public void search_listHit(int search_list_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("search_list.search_list_hit", search_list_num);
	}
	
	
	/* 게시판 글내용보기 */
	public search_list_Bean getsearch_listCont(int search_list_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (search_list_Bean) session.selectOne("search_list.search_list_cont", search_list_num);
	}	

	/* 게시물 수정 */
	public void search_listEdit(search_list_Bean search_list) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("search_list.search_list_edit", search_list);
	}

	/* 게시물 삭제 */
	public void search_listDelete(int search_list_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("search_list.search_list_del", search_list_num);
	}

	/* 답변글 레벨 증가 */
	public void refEdit(search_list_Bean search_list) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("search_list.search_list_Level", search_list);
	}

	/* 답변글 저장 */
	public void search_listReplyOk(search_list_Bean search_list) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("search_list_reply", search_list);
	}
	
}
