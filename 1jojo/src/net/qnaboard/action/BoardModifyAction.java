package net.qnaboard.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.*;

 public class BoardModifyAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 
		 request.setCharacterEncoding("utf-8");
		 ActionForward forward = new ActionForward();
		 
		 boolean result = false;
		 int num=Integer.parseInt(request.getParameter("BOARD_NUM"));
		 String page = request.getParameter("page");
		 String pass = request.getParameter("BOARD_PASS");
		 
		 BoardDAOImpl boarddao=new BoardDAOImpl();
//		 BoardDAO boarddao=new BoardDAO();
		 BoardBean boarddata=new BoardBean();
		 
		 // ë¹„ë?ë²ˆí˜¸ ?¼ì¹? ?—¬ë¶? ?Œë³?
		 /*boolean usercheck=boarddao.isBoardWriter(num, pass);
		 if(usercheck==false){ // ë¹„ë?ë²ˆí˜¸ê°? ?¼ì¹˜í•˜ì§? ?•Š?Š” ê²½ìš°
		   		response.setContentType("text/html;charset=utf-8");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('?ˆ˜? •?•  ê¶Œí•œ?´ ?—†?Šµ?‹ˆ?‹¤.');");
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
		   		System.out.println("ê²Œì‹œ?Œ ?ˆ˜? • ?‹¤?Œ¨");
		   		return null;
		   	 }*/
		   	 System.out.println("ê²Œì‹œ?Œ ?ˆ˜? • ?™„ë£?");
		   	 
		   	 forward.setRedirect(true);
		   	 forward.setPath("./BoardDetailAction.bo?num="+boarddata.getBoard_num()+"&page="+page);
		   	 return forward;
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 
		 return null;
	 }
}