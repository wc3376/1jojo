package m2Action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import m2member.m2memberDAO;
import m2member.m2memberDTO;

public class Login implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Login");
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();		
		
		m2memberDTO member = new m2memberDTO();
		member.setId(request.getParameter("email"));
		member.setPass(request.getParameter("pass"));
		
		m2memberDAO dao = m2memberDAO.getInstance();
		int result = dao.chk(member);
		
		if(result == 1) {
			session.setAttribute("email", member.getId());
			
		}else {
			out.println("<script>");
			out.println("alert('ID나 비밀번호가 틀렸습니다')");
			out.println("history.go(-1)");
			out.print("</script>");
			out.close();
			
			return null;
			}
		
		ActionForward forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("./mainLi.jsp");
				
		return forward;
	}

}
