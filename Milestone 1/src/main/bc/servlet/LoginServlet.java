package main.bc.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/wellness";
        String user = "postgres";
        String password = "sFicA";
        return DriverManager.getConnection(url, user, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = getConnection()) {
            String sql = "SELECT name, password FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String name = rs.getString("name");

                if (storedHash.equals(hashPassword(password))) {
                    // ✅ Generate new token on each login
                    String token = generateToken();

                    // ✅ Store the token in session_token column
                    PreparedStatement tokenStmt = conn.prepareStatement(
                            "UPDATE users SET session_token = ? WHERE email = ?");
                    tokenStmt.setString(1, token);
                    tokenStmt.setString(2, email);
                    tokenStmt.executeUpdate();

                    // ✅ Send token as secure cookie
                    Cookie authCookie = new Cookie("session_token", token);
                    authCookie.setHttpOnly(true);
                    authCookie.setPath("/"); // applies to the whole app
                    authCookie.setMaxAge(60 * 60); // 1 hour
                    response.addCookie(authCookie);

                    // ✅ Optional session attribute for name
                    request.getSession().setAttribute("studentName", name);

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

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        StringBuilder token = new StringBuilder();
        for (byte b : tokenBytes) token.append(String.format("%02x", b));
        return token.toString();
    }
}
