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
	
	
	/* ê²Œì‹œ?Œ ???¥ */
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
			
			result=session.insert("board.board_insert", forumboard);
			System.out.println("result="+result);
		}catch(Exception e){
			System.out.println("result="+result);
			System.out.println(e.getMessage());
		}
		return false;		
	}	
	
	/* ê²Œì‹œ?Œ ì´? ê²Œì‹œë¬? ?ˆ˜ */
	public int forumgetListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("board.board_count")).intValue();	
		 
		return count;
	}
	
	/* ê²Œì‹œë¬? ëª©ë¡ */
	public List<ForumBoardBean> forumgetBoardList(int  page)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		List<ForumBoardBean>  list = session.selectList("board.board_list", page);
	    return list;
	}	

	/* ê²Œì‹œ?Œ ì¡°íšŒ?ˆ˜ ì¦ê? */
	public void forumboardHit(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_hit", board_num);
	}
	
	
	/* ê²Œì‹œ?Œ ê¸??‚´?š©ë³´ê¸° */
	public ForumBoardBean forumgetBoardCont(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (ForumBoardBean) session.selectOne("board.board_cont", board_num);
	}	

	/* ê²Œì‹œë¬? ?ˆ˜? • */
	public void forumboardEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_edit", board);
	}

	/* ê²Œì‹œë¬? ?‚­? œ */
	public void forumboardDelete(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("board.board_del", board_num);
	}

	/* ?‹µë³?ê¸? ? ˆë²? ì¦ê? */
	public void forumrefEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_Level", board);
	}

	/* ?‹µë³?ê¸? ???¥ */
	public void forumboardReplyOk(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("board_reply", board);
	}
	
}
