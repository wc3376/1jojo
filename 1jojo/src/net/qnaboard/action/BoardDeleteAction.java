package net.qnaboard.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.*;

public class BoardDeleteAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	 	throws Exception{
		 System.out.println("1");
		 
		response.setContentType("text/html;charset=utf-8");			
		PrintWriter out=response.getWriter();//μΆλ ₯ ?€?Έλ¦? κ°μ²΄??±
		 
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("utf-8");
		
//	   	boolean result=false;
//	   	boolean usercheck=false;
	   	int num=Integer.parseInt(request.getParameter("num"));
	   	String page = request.getParameter("page");
	   	String pass = request.getParameter("BOARD_PASS");
	   	
	   	BoardDAOImpl boarddao=new BoardDAOImpl();
//	   	BoardDAO1 boarddao=new BoardDAO1();	   	
//	   	usercheck=boarddao.isBoardWriter(num, pass);//	   	
//	   	if(usercheck==false){
//	   		response.setContentType("text/html;charset=utf-8");
//	   		PrintWriter out=response.getWriter();
//	   		out.println("<script>");
//	   		out.println("alert('?­? ?  κΆν?΄ ??΅??€.');");
////	   		out.println("location.href='./BoardList.bo';");
//	   		out.println("history.go(-1)");
//	   		out.println("</script>");
//	   		out.close();
//	   		return null;
//	   	}
	   	
	   	BoardBean board = boarddao.getBoardCont(num);
	   	if(!board.getBoard_pass().equals(pass)){ //λΉλ²?΄ ?ΌμΉνμ§? ?? κ²½μ°
	   		out.println("<script>");
			out.println("alert('λΉλ²?΄ ?€λ¦λ?€!')");
			out.println("history.go(-1)");
			out.println("</script>");
	   		out.close();
	   		return null;
	   		
	   	}else{   	
	   	
//	   	    result=boarddao.boardDelete(num);
	   		boarddao.boardDelete(num);
	   	}// else end
	   	
//	   	if(result==false){
//	   		System.out.println("κ²μ? ?­?  ?€?¨");
//	   		return null;
//	   	}
	   	
//	   	System.out.println("κ²μ? ?­?  ?±κ³?");
//	   	System.out.println("page="+page);
	   	
	   	forward.setRedirect(true);
	   	forward.setPath("./BoardListAction.bo?page="+page);
	   	return forward;	   	
	   	
	 }
}