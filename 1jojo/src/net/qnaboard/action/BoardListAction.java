package net.qnaboard.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.board.db.BoardDAO1;
import net.board.db.BoardDAOImpl;

 public class BoardListAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{

//		BoardDAO boarddao=new BoardDAO();
		BoardDAOImpl boarddao=new BoardDAOImpl();
		List boardlist=new ArrayList();
		
		int page=1; // ?˜„?¬ ?˜?´ì§? ë²ˆí˜¸
		int limit=10; // ?•œ ?™”ë©´ì— ì¶œë ¥?•  ? ˆì½”ë“œ ê°??ˆ˜
		
		if(request.getParameter("page") != null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount=boarddao.getListCount(); //ì´? ë¦¬ìŠ¤?Š¸ ?ˆ˜ë¥? ë°›ì•„?˜´
//		boardlist = boarddao.getBoardList(page,limit); //ë¦¬ìŠ¤?Š¸ë¥? ë°›ì•„?˜´
		boardlist = boarddao.getBoardList(page);
		
		//ì´? ?˜?´ì§? ?ˆ˜
 		int maxpage=(int)((double)listcount/limit+0.95); //0.95ë¥? ?”?•´?„œ ?˜¬ë¦? ì²˜ë¦¬
 		
 		//?˜„?¬ ?˜?´ì§??— ë³´ì—¬ì¤? ?‹œ?‘ ?˜?´ì§? ?ˆ˜(1, 11, 21 ?“±...)
 		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
 		
 		//?˜„?¬ ?˜?´ì§??— ë³´ì—¬ì¤? ë§ˆì?ë§? ?˜?´ì§? ?ˆ˜(10, 20, 30 ?“±...)
		int endpage = startpage+10-1;

 		if(endpage> maxpage) endpage= maxpage;
 		
 		int number = listcount-(page-1)*10; 		
 		
 		request.setAttribute("page", page); //?˜„?¬ ?˜?´ì§? ?ˆ˜
 		request.setAttribute("maxpage", maxpage); //ìµœë? ?˜?´ì§? ?ˆ˜
 		request.setAttribute("startpage", startpage); //?˜„?¬ ?˜?´ì§??— ?‘œ?‹œ?•  ì²? ?˜?´ì§? ?ˆ˜
 		request.setAttribute("endpage", endpage); //?˜„?¬ ?˜?´ì§??— ?‘œ?‹œ?•  ? ?˜?´ì§? ?ˆ˜
		request.setAttribute("listcount",listcount); //ê¸? ?ˆ˜
		request.setAttribute("boardlist", boardlist);		
		
		ActionForward forward= new ActionForward();
	 	forward.setRedirect(false);
 		forward.setPath("/board/qna_board_list.jsp");
 		return forward;
	 }
 }