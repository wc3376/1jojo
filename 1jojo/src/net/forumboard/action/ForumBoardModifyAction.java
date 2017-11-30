package net.forumboard.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.*;

 public class ForumBoardModifyAction implements ForumAction {
	 public ForumActionForward execute(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 
		 request.setCharacterEncoding("utf-8");
		 ForumActionForward forward = new ForumActionForward();
		 
		 boolean result = false;
		 int num=Integer.parseInt(request.getParameter("BOARD_NUM"));
		 String page = request.getParameter("page");
		 String pass = request.getParameter("BOARD_PASS");
		 
		 BoardDAOImpl boarddao=new BoardDAOImpl();
//		 BoardDAO boarddao=new BoardDAO();
		 BoardBean boarddata=new BoardBean();
		 
		 // 비�?번호 ?���? ?���? ?���?
		 /*boolean usercheck=boarddao.isBoardWriter(num, pass);
		 if(usercheck==false){ // 비�?번호�? ?��치하�? ?��?�� 경우
		   		response.setContentType("text/html;charset=utf-8");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('?��?��?�� 권한?�� ?��?��?��?��.');");
		   	//	out.println("location.href='./BoardList.bo';");
		   		out.println("history.go(-1)");
		   		out.println("</script>");
		   		out.close();
		   		return null;
		 }*/
		 
		 try{
			 boarddata.setBoard_num(num);
			 boarddata.setBoard_subject(request.getParameter("BOARD_SUBJECT"));
			 boarddata.setBoard_content(request.getParameter("BOARD_CONTENT"));
			 
		//	 result = boarddao.boardModify(boarddata);
			 boarddao.boardEdit(boarddata);
			 /*if(result==false){
		   		System.out.println("게시?�� ?��?�� ?��?��");
		   		return null;
		   	 }*/
		   	 System.out.println("게시?�� ?��?�� ?���?");
		   	 
		   	 forward.setRedirect(true);
		   	 forward.setPath("./ForumBoardDetailAction.bo?num="+boarddata.getBoard_num()+"&page="+page);
		   	 return forward;
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 
		 return null;
	 }
}