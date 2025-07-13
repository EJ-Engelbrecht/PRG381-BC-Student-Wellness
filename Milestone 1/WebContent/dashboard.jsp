<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%--%>
<%--    String studentName = (String) session.getAttribute("studentName");--%>
<%--    if (studentName == null) {--%>
<%--        response.sendRedirect("login.jsp");--%>
<%--        return;--%>
<%--    }--%>
<%--%>--%>

<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f4f8;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        h2 {
            color: #2c3e50;
            font-size: 28px;
            margin-top: 80px;
        }

        /* Top-right logout button */
        .logout-form {
            position: absolute;
            top: 20px;
            right: 20px;
        }

        .logout-form input[type="submit"] {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 10px 16px;
            font-size: 14px;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .logout-form input[type="submit"]:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

    <form action="LogoutServlet" method="post" class="logout-form">
        <input type="submit" value="Logout">
    </form>

<%--    <h2>Welcome, <%= studentName %>!</h2>--%>

</body>
</html>

