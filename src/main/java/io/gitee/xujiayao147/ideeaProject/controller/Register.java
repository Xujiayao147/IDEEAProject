package io.gitee.xujiayao147.ideeaProject.controller;

import io.gitee.xujiayao147.ideeaProject.bean.User;
import io.gitee.xujiayao147.ideeaProject.service.UserService;
import io.gitee.xujiayao147.ideeaProject.service.impl.UserServiceImpl;
import io.gitee.xujiayao147.ideeaProject.utils.SHA256;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {

	UserService userService = new UserServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.equals("") && password.equals(""))
			request.getRequestDispatcher("/register.html").forward(request, response);

		if (username.equals("") || password.equals(""))
			request.getRequestDispatcher("/response.jsp?success=false&message=3").forward(request, response);

		User user = new User();
		user.setUsername(username);
		user.setPassword(SHA256.getSHA256(password));

		if ((userService.queryRegister(user) == null) && userService.insert(user))
			request.getRequestDispatcher("/response.jsp?success=true&message=4").forward(request, response);
		else
			request.getRequestDispatcher("/response.jsp?success=false&message=5").forward(request, response);
	}
}
