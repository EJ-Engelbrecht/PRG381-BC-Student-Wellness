<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.jsp" />
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="./css/styles.css">
    <script>
        function validateLogin() {
            const email = document.getElementById("email");
            const password = document.getElementById("password");

            const emailMsg = document.getElementById("email-error");
            const passwordMsg = document.getElementById("password-error");

            // Clear old errors
            email.classList.remove("invalid");
            password.classList.remove("invalid");
            emailMsg.innerHTML = "";
            passwordMsg.innerHTML = "";

            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;

            // Step 1: Client-side email format validation
            if (email.value.trim().length === 0) {
                email.classList.add("invalid");
                emailMsg.innerHTML = "<p>Please fill in an Email address.</p>";
                return false;
            }

            if (!emailRegex.test(email.value)) {
                email.classList.add("invalid");
                emailMsg.innerHTML = "<p>Not a real Email address.</p>";
                return false;
            }

            // Let the server validate the email existence and password
            return true;
        }





        function togglePassword() {
            const password = document.getElementById("password");
            const icon = document.getElementById("visImage");

            if (password.type === "password") {
                password.type = "text";
                icon.src = "./images/eye-open.png"; // Change to open eye image
            } else {
                password.type = "password";
                icon.src = "./images/eye-closed.png"; // Change to closed eye image
            }
        }


    </script>
</head>
<body>
<div class="displaycards">
    <button type="button" class="back-arrow" onclick="history.back()" title="Back"></button>

    <h2>Student Login</h2>
    <form action="LoginServlet" method="post" onsubmit="return validateLogin();">
        Email: <input type="email" id="email" name="email" placeholder="Email"><br>
        <div id="email-error" class="error-message"></div>

        Password:
        <div class="password-wrapper">
            <input type="password" id="password" name="password" placeholder="Password">
            <img class="Pimage" src="./images/eye-closed.png" id="visImage" onclick="togglePassword()" alt="Show Password" title="Show Password">
        </div>
        <div id="password-error" class="error-message"></div>


        <input type="submit" value="Login">
    </form>
    <div style="margin-top: 10px; text-align: center;">
        <a href="reset_password.jsp" style="color: #0d6efd; text-decoration: underline;">Forgot your password?</a>
    </div>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>
    <% if (errorMessage != null) { %>
    <div style="color: darkgray; margin-top: 10px;">
        <% if (errorMessage.contains("Email not found")) { %>
        Email not found. Would you like to register?
        <a href="register.jsp" style="color: aliceblue; text-decoration: underline;">Register here</a>
        <% } else { %>
        <%= errorMessage %>
        <% } %>
    </div>
    <% } %>


</div>
</body>
</html>
