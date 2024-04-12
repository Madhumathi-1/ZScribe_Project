package com.zscribeproject;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUp extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String message;
		HttpSession httpSession = request.getSession();
		httpSession.setMaxInactiveInterval(1);
		User user = new User(userName,password,email);
//		System.out.println(user.getUserName() + user.getPassword());
		Register register = new Register();
		boolean isSuccess = register.registerUser(user);
		if(isSuccess) {
			message="success";
			request.setAttribute("message", message);
		}
		else {
			message = "Password do not match. Try again";
			response.sendRedirect("SignUp.html");
		}
		httpSession.setAttribute("message", message);
	}
}