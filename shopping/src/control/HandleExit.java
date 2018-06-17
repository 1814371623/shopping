package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleExit extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public HandleExit() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		HttpSession session = request.getSession(false);
//		session.invalidate();
		HttpSession session = request.getSession(true);//trueһ��Ҫд�����򣬻�ȡ������ǰsessionʱ���Զ�����һ��
		session.invalidate();
		response.sendRedirect("/shopping/jsp/join/login.jsp");
	}

	public void init() throws ServletException {
	}
}
