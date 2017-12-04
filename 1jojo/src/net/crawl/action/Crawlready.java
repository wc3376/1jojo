package net.crawl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAOImpl;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import m2member.m2memberDAO;
import m2member.m2memberDTO;

public class Crawlready implements Action {//딱히 필요는 없어보인다. 그냥 해당 페이지에 id, password가 제대로 되어만 있으면 된다.
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 System.out.println("Crawlready");
	   	try{
			request.setCharacterEncoding("utf-8");

			HttpSession session = request.getSession();
			String email =(String) session.getAttribute("email");
	
			m2memberDAO dao = m2memberDAO.getInstance();
			m2memberDTO member = dao.select(email);
			
			String password=member.getPass();
			
			request.setAttribute("password", password);
	
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);//request로 구해야하므로 dispatcher방식 가능.
			forward.setPath("./crawl/cwl_ready.jsp");
	
			return forward;
			
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return null;
	}  	
}