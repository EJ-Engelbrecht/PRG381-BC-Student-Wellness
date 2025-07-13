<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="card">
        <h2>Student Login</h2>
        <form action="LoginServlet" method="post" onsubmit="return validateLogin();">
            Email: <input type="email" id="email" name="email" placeholder="Email"><br>
            <div id="email-error" class="error-message"></div>


            <div class="password-wrapper">
                <input type="password" id="password" name="password" placeholder="Password">
                <img src="images/eye-closed.png" id="visImage" onclick="togglePassword()" alt="Show Password" title="Show Password">
            </div>
            <div id="password-error" class="error-message"></div>


            <input type="submit" value="Login">
        </form>

<%--        <% --%>
<%--            String error = request.getParameter("error");--%>
<%--            if (error != null) {--%>
<%--                out.println("<p style='color:red; margin-top:10px;'>" + error + "</p>");--%>
<%--            }--%>
<%--        %>--%>
    </div>
</body>
<script src="js/login.js"></script>
</html>
