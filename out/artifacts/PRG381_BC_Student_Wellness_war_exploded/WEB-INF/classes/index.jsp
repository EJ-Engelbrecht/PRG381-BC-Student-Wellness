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
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap');

        /* Background Image & Overlay */
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: 'Poppins', sans-serif;
            background: url('https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1470&q=80') no-repeat center center fixed;
            background-size: cover;
            position: relative;
            overflow: hidden;
        }

        /* Dark overlay for contrast */
        body::before {
            content: "";
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: rgba(0,0,0,0.55);
            z-index: 0;
        }

        /* Container to center content */
        .container {
            position: relative;
            z-index: 1;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            color: white;
            padding: 0 20px;
            animation: fadeIn 1.5s ease forwards;
            opacity: 0;
        }

        /* Heading style */
        h1 {
            font-size: 3rem;
            margin-bottom: 1.5rem;
            text-shadow: 2px 2px 8px rgba(0,0,0,0.7);
        }

        /* Button container */
        .btn-group {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
            justify-content: center;
        }

        /* Buttons with hover effects */
        a.button {
            display: inline-block;
            padding: 15px 40px;
            font-weight: 700;
            font-size: 1.2rem;
            border-radius: 30px;
            text-decoration: none;
            color: white;
            box-shadow: 0 4px 15px rgba(0,0,0,0.3);
            transition: all 0.4s ease;
            position: relative;
            overflow: hidden;
        }

        a.login-btn {
            background: linear-gradient(135deg, #1e90ff, #3498db);
        }
        a.login-btn:hover {
            background: linear-gradient(135deg, #2980b9, #1c5980);
            box-shadow: 0 8px 20px rgba(41, 128, 185, 0.6);
            transform: translateY(-3px);
        }

        a.register-btn {
            background: linear-gradient(135deg, #2ecc71, #27ae60);
        }
        a.register-btn:hover {
            background: linear-gradient(135deg, #239b56, #1f7a43);
            box-shadow: 0 8px 20px rgba(39, 174, 96, 0.6);
            transform: translateY(-3px);
        }

        /* Subtle fade-in animation */
        @keyframes fadeIn {
            to {opacity: 1;}
        }

        /* Add subtle floating animation to buttons */
        a.button {
            animation: floatUp 2s ease-in-out forwards;
        }

        a.login-btn {
            animation-delay: 0s;
        }
        a.register-btn {
            animation-delay: 0.4s;
        }
        @keyframes floatUp {
            0% { transform: translateY(0); }
            50% { transform: translateY(-8px); }
            100% { transform: translateY(0); }
        }

        /* Responsive tweaks */
        @media (max-width: 600px) {
            h1 {
                font-size: 2rem;
            }
            .btn-group {
                flex-direction: column;
                gap: 15px;
            }
            a.button {
                width: 100%;
                font-size: 1.1rem;
            }
        }
    </style>
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
