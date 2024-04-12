package com.zscribeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Register {

    private final String url = "jdbc:mysql://localhost/zscribe";
    private final String userName = "madhumathi";
    private final String password = "mad@1";
    private final String query = "select * from people";
    private final PasswordHashing encrypt = new PasswordHashing();
    public static String recipient;

    public boolean registerUser(User user) {
        boolean registerSuccess = false;
        String queryInsert = "INSERT INTO people (username, password, email) VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, userName, password);
                 PreparedStatement statement = connection.prepareStatement(queryInsert)) {

                statement.setString(1, user.getUserName());
                statement.setString(2, encrypt.hashPassword(user.getPassword()));
                statement.setString(3, user.getEmail());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected < 1) {
                    System.out.println("Username already exists!");
                } else {
                    System.out.println("Registration successful!");
                    recipient = user.getEmail();
                    System.out.println("Email: " + recipient);
                    registerSuccess = true;
                }
            }
        } catch(Exception ee) {
        	System.out.println("Error! while insertion...");
        	ee.printStackTrace();
        }
        return registerSuccess;
    }

    public boolean validateLogin(User user) {
        boolean validateSuccess = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, userName, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String databaseName = resultSet.getString("username");
                    String databasePassword = resultSet.getString("password");
                    String databaseMail = resultSet.getString("email");

                    if (user.getUserName().equals(databaseName)
                            && encrypt.hashPassword(user.getPassword()).equals(databasePassword)) {
                        validateSuccess = true;
                        recipient = databaseMail;
                        System.out.println("Email: " + recipient);
                        break;
                    }
                }
            }
        }catch(Exception ee) {
        	System.out.println("Error! while validating...");
        	ee.printStackTrace();
        }
        return validateSuccess;
    }
}
