<%@ page import="javax.servlet.*" %>
<%
    if (session == null || session.getAttribute("studentName") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String name = (String) session.getAttribute("studentName");
%>
<html>
<head><title>Dashboard</title></head>
<body>
<h2>Welcome, <%= name %>!</h2>
<form method="post" action="LogoutServlet">
    <input type="submit" value="Logout">
</form>
</body>
</html>
