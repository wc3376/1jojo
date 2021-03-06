package net.qnaboard.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class QnaBoardDAOImpl {
	
	private SqlSession getSession() {
		SqlSession session=null;
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader("./QnaSqlMapConfig.xml");
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(reader);
			session = sf.openSession(true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return session;
	}	
	
	
	/* κ²μ? ???₯ */
	public void qnaboardInsert(QnaBoardBean qnaboard) {
		SqlSession session=null;
		int result = 0;		
		try{
			session = getSession(); 
			System.out.println("insert1");
			
			System.out.println("name1="+qnaboard.getBoard_name());
			System.out.println("pass1="+qnaboard.getBoard_pass());
			System.out.println("subject1="+qnaboard.getBoard_subject());
			System.out.println("content1="+qnaboard.getBoard_content());
			System.out.println("file1="+qnaboard.getBoard_file());			
			
			result=session.insert("qnaboard.board_insert", qnaboard);
			System.out.println("result="+result);
		}catch(Exception e){
			System.out.println("result="+result);
			System.out.println(e.getMessage());
		}		
	}	
	
	/* κ²μ? μ΄? κ²μλ¬? ? */
	public int qnagetListCount() throws SQLException {
		int count = 0;
		SqlSession session=null;
		session = getSession();
		count = ((Integer) session.selectOne("qnaboard.board_count")).intValue();	
		 
		return count;
	}
	
	/* κ²μλ¬? λͺ©λ‘ */
	public List<QnaBoardBean> qnagetBoardList(int  page)	throws SQLException {
		SqlSession session=null;
		session = getSession();
		@SuppressWarnings("unchecked")
		List<QnaBoardBean>  list = session.selectList("qnaboard.board_list", page);
	    return list;
	}	

	/* κ²μ? μ‘°ν? μ¦κ? */
	public void qnaboardHit(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("qnaboard.board_hit", board_num);
	}
	
	
	/* κ²μ? κΈ??΄?©λ³΄κΈ° */
	public QnaBoardBean qnagetBoardCont(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		return (QnaBoardBean) session.selectOne("qnaboard.board_cont", board_num);
	}	

	/* κ²μλ¬? ??  */
	public void qnaboardEdit(QnaBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("qnaboard.board_edit", board);
	}

	/* κ²μλ¬? ?­?  */
	public void qnaboardDelete(int board_num) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.delete("qnaboard.board_del", board_num);
	}

	/* ?΅λ³?κΈ? ? λ²? μ¦κ? */
	public void qnarefEdit(QnaBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.update("qnaboard.board_Level", board);
	}

	/* ?΅λ³?κΈ? ???₯ */
	public void qnaboardReplyOk(QnaBoardBean board) throws SQLException {
		SqlSession session=null;
		session = getSession();
		session.insert("qnaboard.board_reply", board);
	}
	
}
