<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Register</title></head>
<body>
<h2>Student Registration</h2>
<form method="post" action="RegisterServlet">
    Student Number: <input type="text" name="studentNumber" required><br>
    Name: <input type="text" name="name" required><br>
    Surname: <input type="text" name="surname" required><br>
    Email: <input type="email" name="email" required><br>
    Phone: <input type="text" name="phone" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Register">
</form>
<p style="color:red;"><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></p>
<p style="color:green;"><%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : "" %></p>
</body>
</html>
