<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.jsp" />
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="./css/styles.css">
    <script>
        function validateLogin() {
            let valid = true;

            const email = document.getElementById("email");
            const password = document.getElementById("password");

            const emailMsg = document.getElementById("email-error");
            const passwordMsg = document.getElementById("password-error");

            // Reset states
            email.classList.remove("invalid");
            password.classList.remove("invalid");
            emailMsg.innerHTML = "";
            passwordMsg.innerHTML = "";

            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;

            // Email validation
            if (email.value.trim().length === 0) {
                email.classList.add("invalid");
                emailMsg.innerHTML += "<p>Please fill in a Email address.</p>";
                valid = false;
            } else {
                //Email complexity validation
                if (!emailRegex.test(email.value)) {
                    email.classList.add("invalid");
                    emailMsg.innerHTML += "<p>Not a real Email address.</p>";
                    valid = false;
                }
            }

            // Password validation
            if (password.value.trim().length === 0) {
                password.classList.add("invalid");
                passwordMsg.innerHTML += "<p>Password cannot be empty.</p>";
                valid = false;
            } else {
                // Password complexity validation
                if (!passwordRegex.test(password.value)) {
                    password.classList.add("invalid");
                    passwordMsg.innerHTML += "<p>Password must contain at least 1 uppercase, 1 digit, and be at least 8 characters.</p>";
                    valid = false;
                }

                // Custom condition
                if (password.value.includes(".")) {
                    password.classList.add("invalid");
                    passwordMsg.innerHTML += "<p>Password cannot contain a dot (.) character.</p>";
                    valid = false;
                }
            }

            return valid;
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

    <%
        String error = request.getParameter("error");
        if (error != null) {
            out.println("<p style='color:red; margin-top:10px;'>" + error + "</p>");
        }
    %>


</div>
</body>
</html>
