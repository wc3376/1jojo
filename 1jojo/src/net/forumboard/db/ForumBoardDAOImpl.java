package net.forumboard.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ForumBoardDAOImpl {
	
	private SqlSession getSession() {
		SqlSession session=null;
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader("./ForumSqlMapConfig.xml");
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(reader);
			session = sf.openSession(true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return session;
	}	
	
	
	/* 게시?�� ???�� */
	public boolean forumboardInsert(ForumBoardBean forumboard) {
		SqlSession session=null;
		int result = 0;		
		try{
			session = getSession(); 
			System.out.println("insert1");
			
			System.out.println("name1="+forumboard.getBoard_name());
			System.out.println("pass1="+forumboard.getBoard_pass());
			System.out.println("subject1="+forumboard.getBoard_subject());
			System.out.println("content1="+forumboard.getBoard_content());
			System.out.println("file1="+forumboard.getBoard_file());			
			
			result=session.insert("forumboard.board_insert", forumboard);
			System.out.println("result="+result);
		}catch(Exception e){
			System.out.println("result="+result);
			System.out.println(e.getMessage());
		}
		return false;		
	}	
	
	/* 게시?�� �? 게시�? ?�� */
	public int forumgetListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("forumboard.board_count")).intValue();	
		 
		return count;
	}
	
	/* 게시�? 목록 */
	public List<ForumBoardBean> forumgetBoardList(int  page)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		@SuppressWarnings("unchecked")
		List<ForumBoardBean>  list = session.selectList("forumboard.board_list", page);
	    return list;
	}	

	/* 게시?�� 조회?�� 증�? */
	public void forumboardHit(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_hit", board_num);
	}
	
	
	/* 게시?�� �??��?��보기 */
	public ForumBoardBean forumgetBoardCont(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (ForumBoardBean) session.selectOne("forumboard.board_cont", board_num);
	}	

	/* 게시�? ?��?�� */
	public void forumboardEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_edit", board);
	}

	/* 게시�? ?��?�� */
	public void forumboardDelete(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("forumboard.board_del", board_num);
	}

	/* ?���?�? ?���? 증�? */
	public void forumrefEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_Level", board);
	}

	/* ?���?�? ???�� */
	public void forumboardReplyOk(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("forumboard.board_reply", board);
	}
	
}
