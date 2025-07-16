package main.bc.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ResetPasswordServlet extends HttpServlet {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/wellness",
                "postgres",
                "sFicA"
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        try (Connection conn = getConnection()) {
            String hashedPassword = hashPassword(newPassword);
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                request.setAttribute("message", "Password updated successfully.");
            } else {
                request.setAttribute("message", "Email not found. Try again.");
            }

            request.getRequestDispatcher("reset_password.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating password. Try again later.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
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