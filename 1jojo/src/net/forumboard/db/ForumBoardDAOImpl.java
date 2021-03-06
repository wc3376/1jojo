package net.forumboard.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import m2member.m2memberDAO;
import m2member.m2memberDTO;

public class ForumBoardDAOImpl {
	
	private static ForumBoardDAOImpl instance = new ForumBoardDAOImpl();
	
	public static ForumBoardDAOImpl getInstance() {
		return instance;
	}
	
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
	
	public int forumchk(ForumBoardBean member) {
		int result = 0;
		SqlSession session = null;
		try {
			session = getSession();
			ForumBoardBean mem = (ForumBoardBean) session.selectOne("select", member.getId());
			if (mem.getId().equals(member.getId())) {
				result = -1;
				if (mem.getPw().equals(member.getPw())) {
					result = 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	/* κ²μ? ???₯ */
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
	
	/* κ²μ? μ΄? κ²μλ¬? ? */
	public int forumgetListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("forumboard.board_count")).intValue();	
		 
		return count;
	}
	
	/* κ²μλ¬? λͺ©λ‘ */
	public List<ForumBoardBean> forumgetBoardList(int  page)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		@SuppressWarnings("unchecked")
		List<ForumBoardBean>  list = session.selectList("forumboard.board_list", page);
	    return list;
	}	

	/* κ²μ? μ‘°ν? μ¦κ? */
	public void forumboardHit(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_hit", board_num);
	}
	
	
	/* κ²μ? κΈ??΄?©λ³΄κΈ° */
	public ForumBoardBean forumgetBoardCont(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (ForumBoardBean) session.selectOne("forumboard.board_cont", board_num);
	}	

	/* κ²μλ¬? ??  */
	public void forumboardEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_edit", board);
	}

	/* κ²μλ¬? ?­?  */
	public void forumboardDelete(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("forumboard.board_del", board_num);
	}

	/* ?΅λ³?κΈ? ? λ²? μ¦κ? */
	public void forumrefEdit(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("forumboard.board_Level", board);
	}

	/* ?΅λ³?κΈ? ???₯ */
	public void forumboardReplyOk(ForumBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("forumboard.board_reply", board);
	}
	
}
