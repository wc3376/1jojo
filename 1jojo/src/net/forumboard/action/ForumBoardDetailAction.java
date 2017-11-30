package net.forumboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAOImpl;

 public class ForumBoardDetailAction implements ForumAction {
	 public ForumActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		request.setCharacterEncoding("utf-8");
   		
		BoardDAOImpl boarddao=new BoardDAOImpl();
//		BoardDAO boarddao=new BoardDAO();
	   	BoardBean boarddata=new BoardBean();
	   	
		int num=Integer.parseInt(request.getParameter("num"));
		String page = request.getParameter("page");

//		boarddao.setReadCountUpdate(num);		
//	   	boarddata=boarddao.getDetail(num);
		boarddao.boardHit(num); //조회?�� 증�?
		boarddata=boarddao.getBoardCont(num);//?��?��보기
	   	
	   	if(boarddata==null){
	   		System.out.println("?��?��보기 ?��?��");
	   		return null;
	   	}
	   	System.out.println("?��?��보기 ?���?");
	   	
	   	request.setAttribute("boarddata", boarddata);
	   	request.setAttribute("page", page);
	   
	   	ForumActionForward forward = new ForumActionForward();
	   	forward.setRedirect(false);
   		forward.setPath("./board/Forum_board_view.jsp");
   		return forward;

	 }
}