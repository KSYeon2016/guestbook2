package kr.ac.sungkyul.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.guestbook.dao.GuestBookDao;
import kr.ac.sungkyul.guestbook.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		if("deleteform".equals(actionName)){
			RequestDispatcher rd = 
					request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
			return;
		} if("add".equals(actionName)){
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);
			
			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);
			
			response.sendRedirect("/guestbook2/gb");
			return;
		} if("delete".equals(actionName)){
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			GuestBookVo vo = new GuestBookVo();
			vo.setNo(Long.parseLong(no));
			vo.setPassword(password);
			
			GuestBookDao dao = new GuestBookDao();
			dao.delete(vo);
			
			response.sendRedirect("/guestbook2/gb");
			return;
		} else {
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getList();
			
			request.setAttribute("list", list);
			
			RequestDispatcher rd = 
					request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
