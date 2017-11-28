package m2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import m2member.m2memberDAO;
import m2member.m2memberDTO;

public class MemberInsert implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberInsert");

		request.setCharacterEncoding("utf-8");		
		
		System.out.println("id="+request.getParameter("email"));
		System.out.println("pass="+request.getParameter("pass"));
		
		
		m2memberDTO member = new m2memberDTO();
		member.setId(request.getParameter("email"));
		member.setPass(request.getParameter("pass"));		
		
				
		m2memberDAO dao = m2memberDAO.getInstance();
		int result = dao.insert(member);
		System.out.println("result="+result);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./loginForm.jsp");		
		
		return forward;
	}

}
