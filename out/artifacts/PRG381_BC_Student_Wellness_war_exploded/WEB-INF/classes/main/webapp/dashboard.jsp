
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>

<%@ page import="java.sql.*, javax.servlet.http.*" %>
<%@ page session="false" %>

<%@ page import="javax.servlet.http.*, java.sql.*" %>

<%@ page import="java.sql.*, javax.servlet.http.*" %>
<%@ page session="false" %>

<%
    // Block browser caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Get token from cookies
    String token = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("session_token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
    }

    // Validate token
    boolean valid = false;
    String studentName = "Unknown";
    if (token != null) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wellness", "postgres", "sFicA");
            PreparedStatement stmt = conn.prepareStatement("SELECT name FROM users WHERE session_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                valid = true;
                studentName = rs.getString("name");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (!valid) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Welcome, <%= studentName %>!</h2>

<form action="LogoutServlet" method="post">
    <input type="submit" value="Logout">
</form>

</body>
</html>
<script>
    window.addEventListener("beforeunload", function () {
        navigator.sendBeacon("LogoutServlet");
    });
</script>
