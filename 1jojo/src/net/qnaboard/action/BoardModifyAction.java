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
		 
		 // λΉλ?λ²νΈ ?ΌμΉ? ?¬λΆ? ?λ³?
		 /*boolean usercheck=boarddao.isBoardWriter(num, pass);
		 if(usercheck==false){ // λΉλ?λ²νΈκ°? ?ΌμΉνμ§? ?? κ²½μ°
		   		response.setContentType("text/html;charset=utf-8");
		   		PrintWriter out=response.getWriter();
		   		out.println("<script>");
		   		out.println("alert('?? ?  κΆν?΄ ??΅??€.');");
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
		   		System.out.println("κ²μ? ??  ?€?¨");
		   		return null;
		   	 }*/
		   	 System.out.println("κ²μ? ??  ?λ£?");
		   	 
		   	 forward.setRedirect(true);
		   	 forward.setPath("./BoardDetailAction.bo?num="+boarddata.getBoard_num()+"&page="+page);
		   	 return forward;
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 
		 return null;
	 }
}