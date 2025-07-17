<%@ page import="java.sql.*, javax.servlet.http.*" %>
<jsp:include page="nav.jsp" />
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
<html lang="en">
<head>
    <title>BC Student Wellness</title>
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>
<div class="container">
    <h1>Welcome to the BC Student Wellness System</h1>

    <div class="btn-group">
        <a href="login.jsp" class="button login-btn">Login</a>
        <a href="register.jsp" class="button register-btn">Register</a>
    </div>
</div>
</body>
</html>