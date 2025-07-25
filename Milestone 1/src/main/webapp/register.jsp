<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.jsp" />
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="./css/styles.css">
    <style>
        /* Modal overlay (same) */
        .modal {
            display: none;
            position: fixed;
            z-index: 999;
            padding-top: 100px;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.55);
            backdrop-filter: blur(4px);
        }

        /* Modal content styled like displaycards */
        .modal-content {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            margin: auto;
            padding: 30px 40px;
            border: none;
            width: 40%;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
            color: white;
        }

        /* Success message heading */
        .modal-content h3 {
            color: #2ecc71;
            font-size: 1.4rem;
            margin-bottom: 20px;
            text-shadow: 1px 1px 4px rgba(0,0,0,0.4);
        }

        /* Login button matching other buttons */
        .login-btn {
            background: linear-gradient(135deg, #1e90ff, #3498db);
            color: white;
            padding: 12px 28px;
            border: none;
            border-radius: 30px;
            font-weight: bold;
            font-size: 1rem;
            cursor: pointer;
            transition: 0.3s ease;
        }

        .login-btn:hover {
            background: linear-gradient(135deg, #2980b9, #1c5980);
            box-shadow: 0 8px 20px rgba(41, 128, 185, 0.6);
            transform: translateY(-2px);
        }

    </style>

    <script>
        function showPasswordStep() {
            document.getElementById("step1").style.display = "none";
            document.getElementById("step2").style.display = "block";
        }

        function backToInfo() {
            document.getElementById("step2").style.display = "none";
            document.getElementById("step1").style.display = "block";
        }

        function validateStep1() {
            let valid = true;

            const email = document.getElementById("email");
            const phone = document.getElementById("phone");
            const id = document.getElementById("student_number");

            const emailMsg = document.getElementById("email-error");
            const phoneMsg = document.getElementById("phone-error");
            const idMsg = document.getElementById("id-error")

            email.classList.remove("invalid");
            phone.classList.remove("invalid");
            id.classList.remove("invalid");
            emailMsg.textContent = "";
            phoneMsg.textContent = "";
            idMsg.textContent = "";

            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            const phoneRegex = /^\(\d{3}\)-\d{3}-\d{4}$/;
            const sixDigitRegex = /^\d{6}$/;

            const studentValue = id.value.trim();
            if (!sixDigitRegex.test(studentValue)) {
                id.classList.add("invalid");
                idMsg.innerHTML = "<p>Student Number must be exactly 6 digits and only numbers.</p>";
                valid = false;
            }

            if (email.value.trim().length === 0) {
                email.classList.add("invalid");
                emailMsg.innerHTML += "<p>Please fill in a Email address.</p>";
                valid = false;
            } else if (!emailRegex.test(email.value)) {
                email.classList.add("invalid");
                emailMsg.innerHTML += "<p>Not a real Email address.</p>";
                valid = false;
            }

            if (!phoneRegex.test(phone.value)) {
                phone.classList.add("invalid");
                phoneMsg.textContent = "Phone number must be in format (123)-456-7890.";
                valid = false;
            }

            if (valid) {
                // 🟢 This is what was missing — you need to copy data into the hidden fields before showing step 2

                const serverError = document.getElementById("server-error-msg");
                if (serverError) serverError.style.display = "none";

                document.getElementById("hidden_student_number").value = document.getElementById("student_number").value;
                document.getElementById("hidden_name").value = document.getElementById("name").value;
                document.getElementById("hidden_surname").value = document.getElementById("surname").value;
                document.getElementById("hidden_email").value = document.getElementById("email").value;
                document.getElementById("hidden_phone").value = document.getElementById("phone").value;

                showPasswordStep();
            }


            return false; // prevent Step 1 form from submitting
        }


        function validateStep2() {
            let valid = true;
            const password = document.getElementById("password");
            const confirm = document.getElementById("confirm-password");
            const passwordMsg = document.getElementById("password-error");


            password.classList.remove("invalid");
            confirm.classList.remove("invalid");
            passwordMsg.textContent = "";


            const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;

            if (password.value.trim().length === 0) {
                password.classList.add("invalid");
                passwordMsg.innerHTML += "<p>Password cannot be empty.</p>";
                valid = false;
            } else if (password.value !== confirm.value) {
                confirm.classList.add("invalid");
                passwordMsg.textContent = "Passwords do not match.";
                valid = false;
            } else if (!passwordRegex.test(password.value)) {
                password.classList.add("invalid");
                passwordMsg.innerHTML += "<p>Password must contain at least 1 uppercase, 1 digit, and be at least 8 characters.</p>";
                valid = false;
            } else if (password.value.includes(".")) {
                password.classList.add("invalid");
                passwordMsg.innerHTML += "<p>Password cannot contain a dot (.) character.</p>";
                valid = false;
            }

            return valid;
        }

        function togglePassword(inputId, toggleId) {
            const passwordField = document.getElementById(inputId);
            const toggleIcon = document.getElementById(toggleId);

            if (passwordField.type === "password") {
                passwordField.type = "text";
                toggleIcon.src = "./images/eye-open.png";
            } else {
                passwordField.type = "password";
                toggleIcon.src = "./images/eye-closed.png";
            }
        }


    </script>
</head>
<body>
<div class="displaycards">
    <h2>Student Registration</h2>


    <!-- Step 1 -->
    <form id="step1" onsubmit="return validateStep1();">
        <button type="button" class="back-arrow" onclick="history.back()" title="Back"></button>
        Student Number: <input type="text" name="student_number" id="student_number" required maxlength="6"><br>
        <div id="id-error" class="error-message"></div>
        Name: <input type="text" name="name" id="name" required><br>
        Surname: <input type="text" name="surname" id="surname" required><br>

        Email: <input type="email" id="email" name="email"><br>
        <div id="email-error" class="error-message"></div>

        Phone: <input type="text" id="phone" name="phone" placeholder="(123)-456-7890" maxlength="14"><br>
        <div id="phone-error" class="error-message"></div>

        <script>
            document.getElementById('phone').addEventListener('input', function (e) {
                let input = e.target;
                let value = input.value.replace(/\D/g, '');
                if (value.length > 10) value = value.slice(0, 10);
                let formatted = '';
                if (value.length > 0) formatted += '(' + value.substring(0, 3);
                if (value.length >= 4) formatted += ')-' + value.substring(3, 6);
                if (value.length >= 7) formatted += '-' + value.substring(6, 10);
                input.value = formatted;
            });
        </script>

        <input type="submit" value="Continue">
    </form>

    <!-- Step 2 -->
    <form id="step2" action="RegisterServlet" method="post" onsubmit="return validateStep2();" style="display:none;">
        <!-- Hidden inputs to persist step1 values -->
        <input type="hidden" name="student_number" id="hidden_student_number">
        <input type="hidden" name="name" id="hidden_name">
        <input type="hidden" name="surname" id="hidden_surname">
        <input type="hidden" name="email" id="hidden_email">
        <input type="hidden" name="phone" id="hidden_phone">

        <script>
            function populateHiddenFields() {
                document.getElementById("hidden_student_number").value = document.getElementById("student_number").value;
                document.getElementById("hidden_name").value = document.getElementById("name").value;
                document.getElementById("hidden_surname").value = document.getElementById("surname").value;
                document.getElementById("hidden_email").value = document.getElementById("email").value;
                document.getElementById("hidden_phone").value = document.getElementById("phone").value;
            }
            // populate hidden fields every time step2 is shown
            document.getElementById("step1").addEventListener("submit", function (e) {
                e.preventDefault();
                if (validateStep1()) {
                    populateHiddenFields();
                }
            });
        </script>

        <button type="button" class="back-arrow" onclick="backToInfo()" title="Back"></button>

        Password:
        <div class="password-wrapper">
            <input type="password" id="password" name="password" placeholder="Password">
            <img class="Pimage" src="./images/eye-closed.png" id="togglePassword1" onclick="togglePassword('password', 'togglePassword1')" alt="Show Password">
        </div>

        Confirm Password:
        <div class="password-wrapper">
            <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password">
            <img class="Pimage" src="./images/eye-closed.png" id="togglePassword2" onclick="togglePassword('confirm-password', 'togglePassword2')" alt="Show Password">
        </div>

        <div id="password-error" class="error-message"></div>

        <input type="submit" value="Register">
    </form>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
    %>

    <% if (errorMessage != null) { %>
    <div id="server-error-msg" style="color: red; font-weight: bold; margin-top: 10px;"><%= errorMessage %></div>
    <% } %>

    <% if (successMessage != null) { %>
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            document.getElementById("successModal").style.display = "block";
        });
    </script>
    <% } %>


    <!-- Success Modal -->
    <div id="successModal" class="modal">
        <div class="modal-content">

            <h3 style="color: white;"><%= successMessage != null ? successMessage : "" %></h3>
            <a href="login.jsp"><button class="login-btn">Go to Login</button></a>
        </div>
    </div>


</div>
</body>
</html>
