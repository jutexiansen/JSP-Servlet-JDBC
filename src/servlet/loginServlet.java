package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aaa.dao.impl.UserDaoImpl;
import com.aaa.entity.User;

// ��չ HttpServlet ��
public class loginServlet extends HttpServlet {
	 // 处理 GET 方法请求的方法
	private static final long serialVersionUID = 1L;
	 
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
 
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		String username = request.getParameter("name");
		String password = request.getParameter("pwd");
		
		/*User user=new UserDaoImpl().login(username, password);
		if(user!=null){
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);
			response.sendRedirect("/WebTest.7/loginSuccess.jsp");
		}else{
			response.sendRedirect("/WebTest.7/login.jsp");
		}*/
		//boolean bool = "admin".equals(username)&&"admin".equals(password);
		boolean bool = new UserDaoImpl().getUser(username, password);
		if (bool) {
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);
			response.sendRedirect("/WebTest.7/loginSuccess.jsp");
		} else {
			response.sendRedirect("/WebTest.7/login.jsp");
		}
	}
}
