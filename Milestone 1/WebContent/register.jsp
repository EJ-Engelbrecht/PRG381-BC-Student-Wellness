<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="css/styles.css">

</head>
<body>
<div class="card">
    <h2>Student Registration</h2>

    <!-- Step 1: Capture Info -->
    <form id="step1" onsubmit="return validateStep1();">
        Student Number: <input type="text" name="studentNumber" required /><br>
        Name: <input type="text" name="name" required><br>
        Surname: <input type="text" name="surname" required><br>

        Email: <input type="email" id="email" name="email"><br>
        <div id="email-error" class="error-message"></div>

        Phone: <input type="text" id="phone" placeholder="(123)-456-7890" maxlength="14"><br>
        <script>

        </script>
        <div id="phone-error" class="error-message"></div>

        <input type="submit" value="Continue">
    </form>


    <!-- Step 2: Password -->
    <form id="step2" action="RegisterServlet" method="post" onsubmit="return validateStep2();" style="display:none;">
        <button type="button" class="back-arrow" onclick="backToInfo()" title="Back"></button>
        <!-- Hidden inputs to carry Step 1 data -->
        <input type="hidden" name="studentNumber" id="studentNumberHidden" />
        <input type="hidden" name="name" id="nameHidden" />
        <input type="hidden" name="surname" id="surnameHidden" />
        <input type="hidden" name="email" id="emailHidden" />
        <input type="hidden" name="phone" id="phoneHidden" />
        <!-- Password-->
        Password:
        <div class="password-wrapper">
            <input type="password" id="password" name="password" placeholder="Password">
            <img src="./images/eye-closed.png" id="togglePassword1" onclick="togglePassword('password', 'togglePassword1')" alt="Show Password" title="Show Password">
        </div>

        <!-- Confirm Password -->
        Confirm Password:
        <div class="password-wrapper">
            <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password">
            <img src="./images/eye-closed.png" id="togglePassword2" onclick="togglePassword('confirm-password', 'togglePassword2')" alt="Show Password" title="Show Password">
        </div>
        <div id="password-error" class="error-message"></div>

        <input type="submit" value="Register">

    </form>

    <p style="color:red;"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></p>

    <% if (request.getAttribute("successMessage") != null) { %>
    <script>
        document.getElementById('step1').style.display = 'none';
        document.getElementById('step2').style.display = 'none';
    </script>
    <p style="color:green;"><%= request.getAttribute("successMessage") %></p>
    <form action="login.jsp" method="get" style="width: 200px; margin: 10px auto;">
        <button type="submit"
                style="
                width: 100%;
                padding: 12px;
                text-align: center;
                border-radius: 8px;
                font-weight: bold;
                background-color: #3498db;
                color: white;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s ease;
            "
                onmouseover="this.style.backgroundColor='#2980b9';"
                onmouseout="this.style.backgroundColor='#3498db';"
        >
            Go to Login
        </button>
    </form>
    <% } %>


</div>

</body>
<script src="./js/register.js"></script>
</html>
