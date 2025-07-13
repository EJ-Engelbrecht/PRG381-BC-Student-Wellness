<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <img src="./images/eye-closed.png" id="visImage" onclick="togglePassword()" alt="Show Password" title="Show Password">
        </div>
        <div id="password-error" class="error-message"></div>


        <input type="submit" value="Login">
    </form>
    <p style="color:red;"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></p>

</div>
</body>
<script src="./js/login.js"></script>
</html>
