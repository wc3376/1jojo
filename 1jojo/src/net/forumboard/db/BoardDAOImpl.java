package net.forumboard.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDAOImpl {
	
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
	
	
	/* κ²μ? ???₯ */
	public void boardInsert(BoardBean board) {
		SqlSession session=null;
		int result = 0;		
		try{
			session = getSession(); 
			System.out.println("insert1");
			
			System.out.println("name1="+board.getBoard_name());
			System.out.println("pass1="+board.getBoard_pass());
			System.out.println("subject1="+board.getBoard_subject());
			System.out.println("content1="+board.getBoard_content());
			System.out.println("file1="+board.getBoard_file());			
			
			result=session.insert("board.board_insert", board);
			System.out.println("result="+result);
		}catch(Exception e){
			System.out.println("result="+result);
			System.out.println(e.getMessage());
		}		
	}	
	
	/* κ²μ? μ΄? κ²μλ¬? ? */
	public int getListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("board.board_count")).intValue();	
		 
		return count;
	}
	
	/* κ²μλ¬? λͺ©λ‘ */
	public List<BoardBean> getBoardList(int  page)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		List<BoardBean>  list = session.selectList("board.board_list", page);
	    return list;
	}	

	/* κ²μ? μ‘°ν? μ¦κ? */
	public void boardHit(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_hit", board_num);
	}
	
	
	/* κ²μ? κΈ??΄?©λ³΄κΈ° */
	public BoardBean getBoardCont(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (BoardBean) session.selectOne("board.board_cont", board_num);
	}	

	/* κ²μλ¬? ??  */
	public void boardEdit(BoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_edit", board);
	}

	/* κ²μλ¬? ?­?  */
	public void boardDelete(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("board.board_del", board_num);
	}

	/* ?΅λ³?κΈ? ? λ²? μ¦κ? */
	public void refEdit(BoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("board.board_Level", board);
	}

	/* ?΅λ³?κΈ? ???₯ */
	public void boardReplyOk(BoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("board_reply", board);
	}
	
}
