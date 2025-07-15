<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*, javax.servlet.http.*" session="true" %>
<jsp:include page="nav.jsp" />
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
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .tracker-bar {
            background-color: #e0e0e0;
            border-radius: 10px;
            height: 10px;
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

        .profile-img {
            width: 15vw;
            max-width: 60px;
            min-width: 40px;
            height: auto;
            border-radius: 50%;
            object-fit: cover;
        }
    </style>
</head>
<body class="bg-light p-4">

<div class="row g-4">
    <!-- LEFT SIDE: Profile + Wellness -->
    <div class="col-md-5 offset-md-1 mt-md-4">
        <!-- Student Profile -->
        <div class="card shadow-sm p-5 mb-4">
            <div class="d-flex align-items-center gap-3">
                <img src="https://api.dicebear.com/6.x/open-peeps/svg?seed=<%= studentName + StudentSurname %>" alt="Avatar" class="profile-img">
                <div>
                    <h5 class="mb-1"><%= studentName + " " + StudentSurname %></h5>
                    <p class="text-muted mb-0">Student ID: <%= StudentNumber %></p>
                </div>
            </div>
        </div>

        <!-- Wellness Tracker -->
        <div class="card shadow-sm p-5">
            <h5>Wellness Tracker</h5>
            <p class="mb-1">Happy</p>
            <div class="tracker-bar mb-2"><div class="bar-fill happy"></div></div>
            <p class="mb-1">Stress</p>
            <div class="tracker-bar mb-2"><div class="bar-fill stress"></div></div>
            <p class="mb-1">Anxiety</p>
            <div class="tracker-bar mb-2"><div class="bar-fill anxiety"></div></div>
            <p class="mb-1">Self-Care</p>
            <div class="tracker-bar mb-3"><div class="bar-fill selfcare"></div></div>
            <div class="d-flex flex-column gap-2">
                <button class="btn btn-success w-100" id="moodCheckInBtn">Mood Check-In</button>
            </div>
        </div>
    </div>

    <!-- RIGHT SIDE: Appointments + Past + Actions -->
    <div class="col-md-5">
        <!-- Upcoming Appointments -->
        <div class="card shadow-sm p-4 mb-4">
            <h5>Upcoming Appointment:</h5>
            <p>Date_of_appointment<br>
                <strong>Enter_Name_of_appointment</strong> - type_of_appointment</p>
            <div class="d-flex flex-wrap gap-2">
                <button class="btn btn-primary flex-grow-1">Reschedule</button>
                <button class="btn btn-dark flex-grow-1">Cancel</button>
            </div>
        </div>

        <!-- Past Sessions -->
        <div class="card shadow-sm p-4 mb-4">
            <h5>Past Sessions</h5>
            <p><strong>Date_of_appointment</strong><br>Enter_Name_of_appointment%> – type_of_appointment</p>
        </div>

        <!-- Quick Actions -->
        <div class="card shadow-sm p-4">
            <h5>Quick Actions</h5>
            <div class="d-grid gap-2">
                <button class="btn btn-primary">Book Appointment</button>
                <button class="btn btn-warning" type="button" data-bs-toggle="modal" data-bs-target="#emergencyHelpModal">Emergency Help</button>

            </div>
        </div>
    </div>
</div>



<!-- Mood Check-In Modal -->
<div class="modal fade" id="moodCheckInModal" tabindex="-1" aria-labelledby="moodCheckInModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" action="SaveMoodServlet" method="post">
            <div class="modal-header">
                <h5 class="modal-title" id="moodCheckInModalLabel">Mood Check-In</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Happy -->
                <label for="happySlider" class="form-label">Happy: <span id="happyValue">60</span>%</label>
                <input type="range" class="form-range" name="happy" id="happySlider" min="0" max="100" value="60">

                <!-- Stress -->
                <label for="stressSlider" class="form-label mt-3">Stress: <span id="stressValue">40</span>%</label>
                <input type="range" class="form-range" name="stress" id="stressSlider" min="0" max="100" value="40">

                <!-- Anxiety -->
                <label for="anxietySlider" class="form-label mt-3">Anxiety: <span id="anxietyValue">70</span>%</label>
                <input type="range" class="form-range" name="anxiety" id="anxietySlider" min="0" max="100" value="70">

                <!-- Self-Care -->
                <label for="selfcareSlider" class="form-label mt-3">Self-Care: <span id="selfcareValue">50</span>%</label>
                <input type="range" class="form-range" name="selfcare" id="selfcareSlider" min="0" max="100" value="50">

                <!-- Hidden field for Student Number -->
                <input type="hidden" name="studentId" value="<%= StudentNumber %>">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Save Changes</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>



<!-- Emergency Help Modal -->
<div class="modal fade" id="emergencyHelpModal" tabindex="-1" aria-labelledby="emergencyHelpModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emergencyHelpModalLabel">Emergency Contacts</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center">Campus Security<span class="fs-6 fw-semibold">012-345-6789</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Mental Health Support<span class="fs-6 fw-semibold">0800-123-456</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const sliders = [
        { id: 'happySlider', label: 'happyValue' },
        { id: 'stressSlider', label: 'stressValue' },
        { id: 'anxietySlider', label: 'anxietyValue' },
        { id: 'selfcareSlider', label: 'selfcareValue' }
    ];

    sliders.forEach(slider => {
        const input = document.getElementById(slider.id);
        const label = document.getElementById(slider.label);
        input.addEventListener('input', () => {
            label.textContent = input.value;
        });
    });

    document.getElementById('moodCheckInBtn').addEventListener('click', () => {
        const modal = new bootstrap.Modal(document.getElementById('moodCheckInModal'));
        modal.show();
    });
</script>


</body>
</html>



