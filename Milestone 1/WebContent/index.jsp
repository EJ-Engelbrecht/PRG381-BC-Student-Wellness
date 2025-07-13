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
<head>
    <title>BC Student Wellness</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #dfe9f3, #ffffff);
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 40px;
        }

        a.button {
            display: block;
            width: 200px;
            padding: 12px;
            margin: 10px 0;
            text-align: center;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        a.login-btn {
            background-color: #3498db;
            color: white;
        }

        a.login-btn:hover {
            background-color: #2980b9;
        }

        a.register-btn {
            background-color: #2ecc71;
            color: white;
        }

        a.register-btn:hover {
            background-color: #27ae60;
        }

    </style>
</head>
<body>
<h1>Welcome to the BC Student Wellness System</h1>

<a href="login.jsp" class="button login-btn">Login</a>
<a href="register.jsp" class="button register-btn">Register</a>
</body>
</html>
