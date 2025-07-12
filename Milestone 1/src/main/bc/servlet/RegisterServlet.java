package main.bc.servlet;

import main.bc.dao.DBConnection;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentNumber = request.getParameter("studentNumber");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            // Check if email or student number already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? OR student_number = ?");
            checkStmt.setString(1, email);
            checkStmt.setString(2, studentNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("errorMessage", "Email or student number already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Insert new user
            String hashedPassword = hashPassword(password);
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)");
            insertStmt.setString(1, studentNumber);
            insertStmt.setString(2, name);
            insertStmt.setString(3, surname);
            insertStmt.setString(4, email);
            insertStmt.setString(5, phone);
            insertStmt.setString(6, hashedPassword);

            insertStmt.executeUpdate();
            request.setAttribute("successMessage", "Registration successful. You can now log in.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
