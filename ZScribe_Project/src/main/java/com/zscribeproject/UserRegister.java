package com.zscribeproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jdbcURL = "jdbc:mysql://localhost:3306/ChatAppUserDetails";
		String username = "madhumathi";
		String password = "mad@1";

//		String userEmail = "murthymadhumathi@gmail.com";
//		String userPassword = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);

			String insertUserQuery = "INSERT INTO User (username, first_name, last_name, dob, create_time_ms, change_time_ms) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement userStatement = connection.prepareStatement(insertUserQuery);
			userStatement.setString(1, "madhum");
			userStatement.setString(2, "madhu");
			userStatement.setString(3, "m");
			userStatement.setString(4, "2003-11-29");
			userStatement.setLong(5, System.currentTimeMillis());
			userStatement.setLong(6, System.currentTimeMillis());
			userStatement.executeUpdate();

//			String insertEmailQuery = "INSERT INTO Email (user_id, email_address, creation_time_ms, change_time_ms) VALUES (?, ?, ?, ?)";
//			PreparedStatement emailStatement = connection.prepareStatement(insertEmailQuery);
//			emailStatement.setInt(1, getUserIdByUsername(connection, "madhum"));
//			emailStatement.setString(2, userEmail);
//			emailStatement.setLong(3, System.currentTimeMillis());
//			emailStatement.setLong(4, System.currentTimeMillis());
//			emailStatement.executeUpdate();
//
//			String insertPasswordQuery = "INSERT INTO Password (user_id, password_hash, creation_time_ms, last_change_time_ms) VALUES (?, ?, ?, ?)";
//			PreparedStatement passwordStatement = connection.prepareStatement(insertPasswordQuery);
//			passwordStatement.setInt(1, getUserIdByUsername(connection, "madhum"));
//			passwordStatement.setString(2, userPassword);
//			passwordStatement.setLong(3, System.currentTimeMillis());
//			passwordStatement.setLong(4, System.currentTimeMillis());
//			passwordStatement.executeUpdate();

			PrintWriter out = response.getWriter();
			out.println("Data inserted successfully");

			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

//	private int getUserIdByUsername(Connection connection, String username) throws SQLException {
//		String query = "SELECT user_id FROM User WHERE username = ?";
//		PreparedStatement statement = connection.prepareStatement(query);
//		statement.setString(1, username);
//		return statement.executeQuery().getInt("user_id");
//	}
}
