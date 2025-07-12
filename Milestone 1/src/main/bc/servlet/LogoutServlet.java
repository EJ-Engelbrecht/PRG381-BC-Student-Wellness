package main.bc.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LogoutServlet extends HttpServlet {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/wellness", "postgres", "sFicA");
    }

    // Accept GET requests from navigator.sendBeacon or manual calls
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = null;

        // Get session_token from cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Invalidate token in database
        if (token != null) {
            try (Connection conn = getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE users SET session_token = NULL WHERE session_token = ?");
                stmt.setString(1, token);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Expire cookie
        Cookie expiredCookie = new Cookie("session_token", "");
        expiredCookie.setMaxAge(0); // expire immediately
        expiredCookie.setPath("/"); // must match original path
        expiredCookie.setHttpOnly(true); // more secure
        response.addCookie(expiredCookie);

        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Only redirect for normal (non-beacon) requests
        String requestedWith = request.getHeader("X-Requested-With");
        if (requestedWith == null || !"XMLHttpRequest".equals(requestedWith)) {
            response.sendRedirect("login.jsp");
        }
    }
}
