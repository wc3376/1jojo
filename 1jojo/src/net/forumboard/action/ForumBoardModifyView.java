package net.forumboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.board.db.BoardDAO1;
import net.board.db.BoardBean;
import net.board.db.BoardDAOImpl;

public class ForumBoardModifyView implements ForumAction {
	 public ForumActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 ForumActionForward forward = new ForumActionForward();
		 	request.setCharacterEncoding("utf-8");
	   		
		 	BoardDAOImpl boarddao=new BoardDAOImpl();
//			BoardDAO boarddao=new BoardDAO();
		   	BoardBean boarddata=new BoardBean();
		   	
			int num=Integer.parseInt(request.getParameter("num"));
			String page = request.getParameter("page");
//		   	boarddata=boarddao.getDetail(num);
			boarddata=boarddao.getBoardCont(num);//�? ?��?�� 
			
		   	if(boarddata==null){
		   		System.out.println("(?��?��)?��?��보기 ?��?��");
		   		return null;
		   	}
		   	System.out.println("(?��?��)?��?��보기 ?���?");
		   	
		   	request.setAttribute("boarddata", boarddata);
		    request.setAttribute("page", page);
		   	forward.setRedirect(false);
	   		forward.setPath("./board/Forum_board_modify.jsp");
	   		return forward;
	 }
}