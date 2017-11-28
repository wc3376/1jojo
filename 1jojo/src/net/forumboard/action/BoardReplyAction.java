package net.forumboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.board.db.BoardDAO1;
import net.board.db.BoardBean;
import net.board.db.BoardDAOImpl;

public class BoardReplyAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	 throws Exception{
		 	request.setCharacterEncoding("utf-8");
		 	ActionForward forward = new ActionForward();
		 	
//			BoardDAO1 boarddao=new BoardDAO1();
		 	BoardDAOImpl boarddao=new BoardDAOImpl();
	   		BoardBean boarddata=new BoardBean();
	   		int result=0;
	   		String page = request.getParameter("page");
	   		
	   		boarddata.setBoard_re_ref(Integer.parseInt(request.getParameter("BOARD_RE_REF")));
	   		boarddata.setBoard_re_seq(Integer.parseInt(request.getParameter("BOARD_RE_SEQ")));
	   		
	   		//ê¸°ì¡´ ?Œ“ê¸??˜ board_re_seqê°’ì„ 1ì¦ê? ?‹œ?‚´
	   		boarddao.refEdit(boarddata);
	   		
	   		//?ƒˆë¡œìš´ ?Œ“ê¸??˜ board_re_levê°’ê³¼ board_re_seqê°’ì„ ë¶?ëª¨ë³´?‹¤ 1ì¦ê??œ ê°’ì„ ?‹¤?‹œ?• ?‹¹
	   		boarddata.setBoard_re_lev(Integer.parseInt(request.getParameter("BOARD_RE_LEV"))+1);
	   		boarddata.setBoard_re_seq(Integer.parseInt(request.getParameter("BOARD_RE_SEQ"))+1);
	   		
	   		boarddata.setBoard_num(Integer.parseInt(request.getParameter("BOARD_NUM")));
	   		boarddata.setBoard_name(request.getParameter("BOARD_NAME"));
	   		boarddata.setBoard_pass(request.getParameter("BOARD_PASS"));
	   		boarddata.setBoard_subject(request.getParameter("BOARD_SUBJECT"));
	   		boarddata.setBoard_content(request.getParameter("BOARD_CONTENT"));	   		
	   		
//	   		result=boarddao.boardReply(boarddata);
	   		boarddao.boardReplyOk(boarddata);
//	   		if(result==0){
//	   			System.out.println("?‹µ?¥ ?‹¤?Œ¨");
//	   			return null;
//	   		}
	   		System.out.println("?‹µ?¥ ?™„ë£?");
	   		
	   		forward.setRedirect(true);
	   		forward.setPath("./BoardDetailAction.bo?num="+boarddata.getBoard_num()+"&page="+page);
//	   		forward.setPath("./BoardDetailAction.bo?num="+result+"&page="+page);
	   		return forward;
	}  	
}