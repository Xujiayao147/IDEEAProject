package io.gitee.xujiayao147.ideeaProject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/login")
public class Test extends HttpServlet {

	@Serial
	private static final long serialVersionUID = 1L;

	// http://localhost:8080/login?username=admin&password=1234

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		UserDAO userDAO = new UserDAO();

		if (userDAO.query(user) != null)
			request.getRequestDispatcher("/success.html").forward(request, response);
		else
			request.getRequestDispatcher("/fail.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
