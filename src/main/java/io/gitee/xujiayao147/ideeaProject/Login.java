package io.gitee.xujiayao147.ideeaProject;

import io.gitee.xujiayao147.ideeaProject.bean.User;
import io.gitee.xujiayao147.ideeaProject.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null || password == null)
			request.getRequestDispatcher("/login.html").forward(request, response);

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		UserDAO userDAO = new UserDAO();

		if (userDAO.query(user) != null)
			request.getRequestDispatcher("/response.jsp?success=true").forward(request, response);
		else
			request.getRequestDispatcher("/response.jsp?success=false").forward(request, response);
	}
}
