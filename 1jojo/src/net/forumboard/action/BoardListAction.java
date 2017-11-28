package net.forumboard.action;

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
		
		int page=1; // ??¬ ??΄μ§? λ²νΈ
		int limit=10; // ? ?λ©΄μ μΆλ ₯?  ? μ½λ κ°??
		
		if(request.getParameter("page") != null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount=boarddao.getListCount(); //μ΄? λ¦¬μ€?Έ ?λ₯? λ°μ?΄
//		boardlist = boarddao.getBoardList(page,limit); //λ¦¬μ€?Έλ₯? λ°μ?΄
		boardlist = boarddao.getBoardList(page);
		
		//μ΄? ??΄μ§? ?
 		int maxpage=(int)((double)listcount/limit+0.95); //0.95λ₯? ??΄? ?¬λ¦? μ²λ¦¬
 		
 		//??¬ ??΄μ§?? λ³΄μ¬μ€? ?? ??΄μ§? ?(1, 11, 21 ?±...)
 		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
 		
 		//??¬ ??΄μ§?? λ³΄μ¬μ€? λ§μ?λ§? ??΄μ§? ?(10, 20, 30 ?±...)
		int endpage = startpage+10-1;

 		if(endpage> maxpage) endpage= maxpage;
 		
 		int number = listcount-(page-1)*10; 		
 		
 		request.setAttribute("page", page); //??¬ ??΄μ§? ?
 		request.setAttribute("maxpage", maxpage); //μ΅λ? ??΄μ§? ?
 		request.setAttribute("startpage", startpage); //??¬ ??΄μ§?? ???  μ²? ??΄μ§? ?
 		request.setAttribute("endpage", endpage); //??¬ ??΄μ§?? ???  ? ??΄μ§? ?
		request.setAttribute("listcount",listcount); //κΈ? ?
		request.setAttribute("boardlist", boardlist);		
		
		ActionForward forward= new ActionForward();
	 	forward.setRedirect(false);
 		forward.setPath("/board/qna_board_list.jsp");
 		return forward;
	 }
 }