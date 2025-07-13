<%@ page import="java.sql.*, javax.servlet.http.*" %>

<%
    Cookie[] cookies = request.getCookies();
    String token = null;

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("session_token".equals(cookie.getName())) {
                token = cookie.getValue();

                // Invalidate token in DB
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wellness", "postgres", "sFicA");
                    PreparedStatement stmt = conn.prepareStatement("UPDATE users SET session_token = NULL WHERE session_token = ?");
                    stmt.setString(1, token);
                    stmt.executeUpdate();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Clear cookie
                Cookie clear = new Cookie("session_token", "");
                clear.setPath("/");
                clear.setMaxAge(0); // Expire immediately
                response.addCookie(clear);
                break;
            }
        }
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>BC Student Wellness</title></head>
<body>
<h2>Welcome to the BC Student Wellness Portal</h2>
<a href="login.jsp">Login</a> | <a href="register.jsp">Register</a>
</body>
</html>
