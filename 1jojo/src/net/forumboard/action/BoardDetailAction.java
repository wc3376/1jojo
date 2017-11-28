package net.forumboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAOImpl;

 public class BoardDetailAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		request.setCharacterEncoding("utf-8");
   		
		BoardDAOImpl boarddao=new BoardDAOImpl();
//		BoardDAO boarddao=new BoardDAO();
	   	BoardBean boarddata=new BoardBean();
	   	
		int num=Integer.parseInt(request.getParameter("num"));
		String page = request.getParameter("page");

//		boarddao.setReadCountUpdate(num);		
//	   	boarddata=boarddao.getDetail(num);
		boarddao.boardHit(num); //Ï°∞Ìöå?àò Ï¶ùÍ?
		boarddata=boarddao.getBoardCont(num);//?Ç¥?ö©Î≥¥Í∏∞
	   	
	   	if(boarddata==null){
	   		System.out.println("?ÉÅ?Ñ∏Î≥¥Í∏∞ ?ã§?å®");
	   		return null;
	   	}
	   	System.out.println("?ÉÅ?Ñ∏Î≥¥Í∏∞ ?Ñ±Í≥?");
	   	
	   	request.setAttribute("boarddata", boarddata);
	   	request.setAttribute("page", page);
	   
	   	ActionForward forward = new ActionForward();
	   	forward.setRedirect(false);
   		forward.setPath("./board/qna_board_view.jsp");
   		return forward;

	 }
}