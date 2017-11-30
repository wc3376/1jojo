package net.forumboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.board.db.BoardDAO1;
import net.forumboard.db.ForumBoardBean;
import net.forumboard.db.ForumBoardDAOImpl;

public class ForumBoardReplyView implements ForumAction {
	 public ForumActionForward excute(HttpServletRequest request,HttpServletResponse response) 
	 	throws Exception{
		 	ForumActionForward forward = new ForumActionForward();
		 	
//			BoardDAO1 boarddao=new BoardDAO1();
		 	ForumBoardDAOImpl boarddao=new ForumBoardDAOImpl();
		 	ForumBoardBean boarddata=new ForumBoardBean();
	   		
	   		int num=Integer.parseInt(request.getParameter("num"));
	   		String page = request.getParameter("page");
	   		
//	   		boarddata=boarddao.getDetail(num);
	   		boarddata=boarddao.getBoardCont(num);
	   		
	   		if(boarddata==null){
	   			System.out.println("?��?�� ?��?���? ?��?�� ?��?��");
	   			return null;
	   		}
	   		System.out.println("?��?�� ?��?���? ?��?�� ?���?");
	   		
	   		request.setAttribute("boarddata", boarddata);
	   		request.setAttribute("page", page);
	   		
	   		forward.setRedirect(false);
	   		forward.setPath("./board/Forum_board_reply.jsp");
	   		return forward;
	}

	@Override
	public ForumActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}