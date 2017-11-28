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

import dao.MemberDAO;
import dto.MemberDTO;
import m2member.m2memberDAO;
import m2member.m2memberDTO;

public class Crawlready implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 System.out.println("Crawlready");
	   	try{
			request.setCharacterEncoding("utf-8");

			HttpSession session = request.getSession();
			String id =(String) session.getAttribute("id");
	
			m2memberDAO dao = m2memberDAO.getInstance();
			m2memberDTO member = dao.getMember(id);
			
			String identify=member.getId();
			String password=member.getPass();
			
			request.setAttribute("id", identify);//EL로 구해와서 출력해야됨.
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