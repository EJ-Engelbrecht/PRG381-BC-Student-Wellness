<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*, javax.servlet.http.*" session="true" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>

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
    String StudentNumber = "Unknown";
    String StudentSurname = "Unknown";
    if (token != null) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wellness", "postgres", "sFicA");
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT student_number, name, surname FROM users WHERE session_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                valid = true;
                StudentNumber = rs.getString("student_number");
                studentName = rs.getString("name");
                StudentSurname = rs.getString("surname");

                // Save to session for later use
                session.setAttribute("StudentNumber", StudentNumber);
                session.setAttribute("studentName", studentName);
                session.setAttribute("StudentSurname", StudentSurname);
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


<html>
<head>
    <title>Student Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fa;
            margin: 0;
            padding: 30px;
        }

        .grid-container {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-gap: 20px;
            max-width: 1000px;
            margin: 0 auto;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.08);
        }

        .header {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .header img {
            width: 60px;
            height: 60px;
            border-radius: 50%;
        }

        .header-details {
            text-align: left;
        }

        .header-details h3 {
            margin: 0;
            color: #2c3e50;
        }

        .header-details p {
            margin: 3px 0;
            color: #666;
        }

        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 10px 20px;
            margin-top: 10px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #2980b9;
        }

        .tracker-bar {
            background-color: #e0e0e0;
            border-radius: 10px;
            height: 10px;
            margin-top: 5px;
            overflow: hidden;
        }

        .bar-fill {
            height: 100%;
            border-radius: 10px;
        }

        .happy { background-color: #2ecc71; width: 60%; }
        .stress { background-color: #f1c40f; width: 40%; }
        .anxiety { background-color: #e67e22; width: 70%; }
        .selfcare { background-color: #9b59b6; width: 50%; }

        .bottom-actions {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
            gap: 15px;
        }

        .bottom-actions button {
            flex: 1;
            padding: 14px;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 12px;
            font-weight: bold;
            cursor: pointer;
        }

        .book-btn { background-color: #2980b9; }
        .mood-btn { background-color: #2ecc71; }
        .emergency-btn { background-color: #e67e22; }

    </style>
</head>
<body>

<div class="grid-container">
    <!-- Student Profile -->
    <div class="card">
        <div class="header">
            <img src="https://api.dicebear.com/6.x/open-peeps/svg?seed=John" alt="Avatar"> <!-- random profile -->
            <div class="header-details">
                <h3><%= studentName + " " + StudentSurname%></h3>
                <p>Student ID: <%= StudentNumber %></p>

                <p></p>   <!--Can add more detail-->
            </div>
        </div>
    </div>

    <!-- Upcoming Appointments -->
    <div class="card">
        <h3>Upcoming Appointments</h3>
        <p><strong>Dr. Smith</strong> - Counseling</p>
        <button class="btn">Reschedule</button>
        <button class="btn" style="background-color: #2c3e50; margin-left: 10px;">Cancel</button>
    </div>

    <!-- Past Sessions -->
    <div class="card">
        <h3>Past Sessions</h3>
        <p><strong>April 19, 2024</strong><br>Dr. Smith – Academic Support</p>
        <button class="btn">Submit Anonymous Feedback</button>
    </div>

    <!-- Wellness Tracker -->
    <div class="card">
        <h3>Wellness Tracker</h3>
        <p>Happy</p>
        <div class="tracker-bar"><div class="bar-fill happy" id="happyBar"></div></div>

        <p>Stress</p>
        <div class="tracker-bar"><div class="bar-fill stress" id="stressBar"></div></div>

        <p>Anxiety</p>
        <div class="tracker-bar"><div class="bar-fill anxiety" id="anxietyBar"></div></div>

        <p>Self-Care</p>
        <div class="tracker-bar"><div class="bar-fill selfcare" id="selfcareBar"></div></div>

        <button class="btn" id="moodCheckInBtn">Mood Check-In</button>
        <button class="btn" id="emergencyBtn">Emergency Contacts</button>
    </div>

    <!-- Book a Session -->
    <div class="card" style="grid-column: span 2; text-align: center;">
        <button class="btn" style="font-size: 18px;">Book a Session</button>
    </div>
</div>

<!-- Bottom Buttons -->
<div class="grid-container" style="grid-template-columns: repeat(3, 1fr); margin-top: 20px;">
    <button class="book-btn">📅 Book Appointment</button>
    <button class="mood-btn">😊 Mood Check-In</button>
    <button class="emergency-btn">⚠ Emergency Help</button>
</div>

</body>
</html>

