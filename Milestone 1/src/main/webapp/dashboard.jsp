
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>

<%@ page import="java.sql.*, javax.servlet.http.*" %>
<%@ page session="false" %>

<%@ page import="javax.servlet.http.*, java.sql.*" %>

<%@ page import="java.sql.*, javax.servlet.http.*" %>
<%@ page session="false" %>

<%
    // Block browser caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Get token from cookies
    String token = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("session_token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
    }

    // Validate token
    boolean valid = false;
    String studentName = "Unknown";
    if (token != null) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wellness", "postgres", "sFicA");
            PreparedStatement stmt = conn.prepareStatement("SELECT name FROM users WHERE session_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                valid = true;
                studentName = rs.getString("name");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (!valid) {
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <style>
        /* Page background and font */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fa;
            padding: 30px;
            text-align: center;
        }

        /* Card container */
        .dashboard-card {
            background: white;
            padding: 20px 40px;
            margin: 0 auto;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            max-width: 600px;
            position: relative; /* Needed for absolute children */
        }

        /* Student name in color */
        .student-name {
            color: #3673b9;
            font-weight: bold;
        }

        /* Dot & status */
        #session-info {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 14px;
            color: #666;
        }

        .status-dot {
            height: 12px;
            width: 12px;
            border-radius: 50%;
            display: inline-block;
            margin-left: 6px;
        }

        .status-active {
            background-color: green;
        }

        .status-inactive {
            background-color: red;
        }

        /* Logout button container (top-left) */
        #logout-container {
            position: absolute;
            top: 10px;
            left: 15px;
        }

        /* Logout button styling */
        #logout-button {
            background-color: #e74c3c;
            color: white;
            font-weight: bold;
            padding: 8px 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        #logout-button:hover {
            background-color: #c0392b;
        }

        /* General button (blue) */
        .dashboard-btn {
            padding: 10px 20px;
            background-color: #3498db;
            border: none;
            color: white;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }

        .dashboard-btn:hover {
            background-color: #2980b9;
        }

        /* To-do list container */
        #todo-list {
            list-style-type: none;
            padding: 0;
        }

        /* To-do list items */
        #todo-list li {
            padding: 8px;
            text-align: left;
            background: #ecf0f1;
            margin: 5px auto;
            max-width: 400px;
            border-radius: 6px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        /* Crossed-out task */
        #todo-list li.done span {
            text-decoration: line-through;
            color: gray;
        }

    </style>
</head>
<body>

<div class="dashboard-card">
    <div id="logout-container">
        <form action="LogoutServlet" method="post">
            <button type="submit" id="logout-button">Logout</button>
        </form>
    </div>

    <!-- Top-right "Active Status" -->
    <div id="session-info">
        <p id="activestatus">
            Active Status:
            <span id="status-dot" class="status-dot status-active" title="Session Active"></span>
        </p>
    </div>

    <!-- Greeting with colored student name -->
    <h2>Welcome, <span class="student-name"><%= studentName %></span>!</h2>
    <h3>Activity List:</h3>
    <input type="text" id="todo-input" placeholder="Enter new task..." style="padding: 8px; width: 70%;">
    <button onclick="addTodo()" class="dashboard-btn">Add</button>
    <ul id="todo-list"></ul>

</div>


<script>
    // --- Activity Tracking & Inactive Timer ---
    let timeout;
    const statusDot = document.getElementById("status-dot");

    function setInactive() {
        statusDot.classList.remove("status-active");
        statusDot.classList.add("status-inactive");
        statusDot.title = "Inactive (1+ min)";
    }

    function setActive() {
        statusDot.classList.remove("status-inactive");
        statusDot.classList.add("status-active");
        statusDot.title = "Session Active";
        resetTimer();
    }

    function resetTimer() {
        clearTimeout(timeout);
        timeout = setTimeout(setInactive, 0.1 * 60 * 1000); // change the first number for minutes 15 = 15min (Currently 6 seconds for demonstration purposes)
    }

    document.addEventListener("click", setActive);
    document.addEventListener("keydown", setActive);
    document.addEventListener("mousemove", setActive);
    document.addEventListener("scroll", setActive);
    resetTimer();



    // --- To-Do List Functionality ---
    function addTodo() {
        const input = document.getElementById("todo-input");
        const taskText = input.value.trim();
        if (taskText === "") return;

        // Create elements
        const li = document.createElement("li");
        const span = document.createElement("span");
        span.textContent = taskText;

        const toggleBtn = document.createElement("button");
        toggleBtn.textContent = "Done";
        toggleBtn.style.marginLeft = "10px";
        toggleBtn.style.padding = "4px 10px";
        toggleBtn.style.borderRadius = "6px";
        toggleBtn.style.border = "none";
        toggleBtn.style.cursor = "pointer";
        toggleBtn.style.backgroundColor = "#27ae60";
        toggleBtn.style.color = "white";
        toggleBtn.style.fontWeight = "bold";

        toggleBtn.onclick = function () {
            if (li.classList.contains("done")) {
                li.classList.remove("done");
                toggleBtn.textContent = "Done";
                toggleBtn.style.backgroundColor = "#27ae60";
            } else {
                li.classList.add("done");
                toggleBtn.textContent = "Undo";
                toggleBtn.style.backgroundColor = "#e67e22";
            }
        };

        li.appendChild(span);
        li.appendChild(toggleBtn);
        document.getElementById("todo-list").appendChild(li);

        input.value = "";
    }
</script>

</body>
</html>

