<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.jsp" />
<%
    String studentEmail = (String) session.getAttribute("studentEmail"); // from login
    boolean fromDashboard = studentEmail != null;
%>
<html>
<head>
    <title><%= fromDashboard ? "Change Password" : "Reset Password" %></title>
    <link rel="stylesheet" href="./css/styles.css">
    <style>
        .password-wrapper {
            display: flex;
            align-items: center;
        }
        .password-wrapper input {
            flex: 1;
        }
        .Pimage {
            width: 24px;
            margin-left: -30px;
            cursor: pointer;
        }
    </style>
    <script>
        function togglePassword(inputId, toggleId) {
            const input = document.getElementById(inputId);
            const icon = document.getElementById(toggleId);
            if (input.type === "password") {
                input.type = "text";
                icon.src = "./images/eye-open.png";
            } else {
                input.type = "password";
                icon.src = "./images/eye-closed.png";
            }
        }
    </script>
</head>
<body>
<div class="displaycards">
    <button type="button" class="back-arrow" onclick="history.back()" title="Back"></button>
    <h2><%= fromDashboard ? "Change Your Password" : "Reset Your Password" %></h2>

    <form action="ResetPasswordServlet" method="post">
        <% if (!fromDashboard) { %>
        <label>Email:</label>
        <input type="email" name="email" required><br><br>
        <% } else { %>
        <input type="hidden" name="email" value="<%= studentEmail %>">
        <% } %>

        <label>New Password:</label>
        <div class="password-wrapper">
            <input type="password" id="newPassword" name="newPassword" required>
            <img class="Pimage" src="./images/eye-closed.png" id="togglePassword1"
                 onclick="togglePassword('newPassword', 'togglePassword1')" alt="Show Password">
        </div><br>

        <label>Confirm Password:</label>
        <div class="password-wrapper">
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <img class="Pimage" src="./images/eye-closed.png" id="togglePassword2"
                 onclick="togglePassword('confirmPassword', 'togglePassword2')" alt="Show Password">
        </div><br>

        <input type="submit" value="<%= fromDashboard ? "Change Password" : "Reset Password" %>">
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p style="color:#0d6efd; margin-top: 10px;"><%= message %></p>
    <%
        }
    %>
</div>
</body>
</html>
