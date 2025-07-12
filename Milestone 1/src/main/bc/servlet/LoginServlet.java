package main.bc.servlet;

import main.bc.dao.DBConnection;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, password FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String name = rs.getString("name");

                if (storedHash.equals(hashPassword(password))) {
                    HttpSession session = request.getSession();
                    session.setAttribute("studentName", name);
                    response.sendRedirect("dashboard.jsp");
                    return;
                }
            }

            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
