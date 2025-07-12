<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="css/styles.css">
    <script>
        function validateForm() {
            const email = document.forms["regForm"]["email"].value;
            const password = document.forms["regForm"]["password"].value;
            const phone = document.forms["regForm"]["phone"].value;

            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            const phoneRegex = /^[0-9]{10}$/;

            if (!emailRegex.test(email)) {
                alert("Invalid email format");
                return false;
            }
            if (password.length < 8) {
                alert("Password must be at least 8 characters");
                return false;
            }
            if (!phoneRegex.test(phone)) {
                alert("Invalid phone number");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<h2>Student Registration</h2>
<form name="regForm" action="RegisterServlet" method="post" onsubmit="return validateForm();">
    Student Number: <input type="text" name="student_number" required><br>
    Name: <input type="text" name="name" required><br>
    Surname: <input type="text" name="surname" required><br>
    Email: <input type="email" name="email" required><br>
    Phone: <input type="text" name="phone" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Register">
</form>
</body>
</html>
