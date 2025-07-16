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
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="./css/styles.css">
    <style>
        .tracker-bar {
            background-color: #e0e0e0;
            border-radius: 10px;
            height: 10px;
            overflow: hidden;
        }

        .card {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            color: white;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
            animation: fadeIn 1.5s ease forwards;
            opacity: 0;
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

        .bg-purple {
            background-color: #a259ff !important;
        }

        .modal-content {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(12px);
            border-radius: 15px;
            color: white;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .modal-header, .modal-body, .modal-footer {
            border: none;
        }

        .goal-item {
            background-color: rgba(255, 255, 255, 0.08);
            padding: 0.5rem 1rem;
            border-radius: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: white;
            margin-bottom: 0.5rem;
        }

        .goal-item.completed {
            text-decoration: line-through;
            opacity: 0.7;
        }

        .goal-item button {
            margin-left: 0.5rem;
        }
    </style>
</head>
<body class="bg-light p-4 dashboard">
<div class="text-center mb-4">
    <h2 class="fw-bold text-dark" style="margin-bottom: 4%; margin-top: 2%;">Student Wellness Dashboard</h2>
</div>
<div class="container-fluid px-lg-5">
    <div class="row g-4">
        <div class="col-md-5 offset-md-1 mt-md-4">
            <div class="card shadow-sm p-5 mb-4">
                <div class="d-flex align-items-center gap-3">
                    <img src="https://api.dicebear.com/6.x/open-peeps/svg?seed=<%= studentName + StudentSurname %>" alt="Avatar" class="profile-img">
                    <div>
                        <h5 class="mb-1"><%= studentName + " " + StudentSurname %></h5>
                        <p class="mb-0" style="color: black;">Student ID: <%= StudentNumber %></p>
                    </div>
                </div>
            </div>
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

        <div class="col-md-5">
            <div class="card shadow-sm p-4 mb-4 text-center">
                <div class="mx-auto bg-info bg-opacity-25 rounded-circle d-flex align-items-center justify-content-center" style="width: 60px; height: 60px;">
                    <span id="goalCount" class="text-info fw-bold fs-4">0</span>
                </div>
                <h5 class="fw-bold mt-3 mb-3 text-light">Current Goals</h5>
                <div class="progress rounded-pill mb-3" style="height: 15px;">
                    <div id="goalProgress" class="progress-bar bg-purple" role="progressbar" style="width: 0%; border-radius: 50px;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <button class="btn btn-primary btn-lg w-100" onclick="openGoalsModal()">Manage Goals</button>
            </div>

            <div class="card shadow-sm p-4">
                <h5>Quick Actions</h5>
                <div class="d-grid gap-2">
                    <button class="btn btn-primary">Book Appointment</button>
                    <button class="btn btn-warning" type="button" data-bs-toggle="modal" data-bs-target="#emergencyHelpModal">Emergency Help</button>
                </div>
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
                <label for="happySlider" class="form-label">Happy: <span id="happyValue">60</span>%</label>
                <input type="range" class="form-range" name="happy" id="happySlider" min="0" max="100" value="60">
                <label for="stressSlider" class="form-label mt-3">Stress: <span id="stressValue">40</span>%</label>
                <input type="range" class="form-range" name="stress" id="stressSlider" min="0" max="100" value="40">
                <label for="anxietySlider" class="form-label mt-3">Anxiety: <span id="anxietyValue">70</span>%</label>
                <input type="range" class="form-range" name="anxiety" id="anxietySlider" min="0" max="100" value="70">
                <label for="selfcareSlider" class="form-label mt-3">Self-Care: <span id="selfcareValue">50</span>%</label>
                <input type="range" class="form-range" name="selfcare" id="selfcareSlider" min="0" max="100" value="50">
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
                    <li class="list-group-item d-flex justify-content-between align-items-center">Campus Security<span class="fs-6 fw-semibold">012-345-6789</span></li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Mental Health Support<span class="fs-6 fw-semibold">0800-123-456</span></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Goals Modal -->
<div class="modal fade" id="goalsModal" tabindex="-1" aria-labelledby="goalsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-4">
            <div class="modal-header">
                <h5 class="modal-title" id="goalsModalLabel">Manage Your Goals</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3 d-flex gap-2">
                    <input type="text" id="goalInput" class="form-control" placeholder="Enter new goal">
                    <button class="btn btn-success btn-sm px-3" onclick="addGoal(event)">Add</button>
                </div>
                <div id="goalList"></div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>

    const goalList = document.getElementById('goalList');
    const goalInput = document.getElementById('goalInput');
    const goalCount = document.getElementById('goalCount');
    const goalProgress = document.getElementById('goalProgress');

    let goals = [];

    function openGoalsModal() {
        const modal = new bootstrap.Modal(document.getElementById('goalsModal'));
        modal.show();
    }

    function addGoal(e) {
        e.preventDefault();
        const text = goalInput.value.trim(); // Ensure text is trimmed properly
        if (text !== '') {
            goals.push({ text: text, done: false }); // Ensure text is correctly added to goals array
            goalInput.value = '';
            renderGoals(); // Re-render the goals list
        }
    }

    function toggleComplete(index) {
        goals[index].done = !goals[index].done;
        renderGoals();
    }

    function removeGoal(index) {
        goals.splice(index, 1);
        renderGoals();
    }

    function renderGoals() {
        goalList.innerHTML = '';
        goals.forEach((goal, i) => {
            const goalDiv = document.createElement('div');
            goalDiv.className = 'goal-item' + (goal.done ? ' completed' : '');

            const span = document.createElement('span');
            span.textContent = goal.text;

            const buttonGroup = document.createElement('div');

            const removeBtn = document.createElement('button');
            removeBtn.className = 'btn btn-sm btn-danger';
            removeBtn.textContent = 'Remove';
            removeBtn.addEventListener('click', () => removeGoal(i));

            const completeBtn = document.createElement('button');
            completeBtn.className = 'btn btn-sm btn-success';
            completeBtn.textContent = goal.done ? 'Undo' : 'Complete';
            completeBtn.addEventListener('click', () => toggleComplete(i));

            buttonGroup.appendChild(removeBtn);
            buttonGroup.appendChild(completeBtn);

            goalDiv.appendChild(span);
            goalDiv.appendChild(buttonGroup);

            goalList.appendChild(goalDiv);
        });

        goalCount.textContent = goals.length;
        const doneCount = goals.filter(g => g.done).length;
        const progress = goals.length > 0 ? Math.round((doneCount / goals.length) * 100) : 0;
        goalProgress.style.width = progress + '%';
        goalProgress.setAttribute('aria-valuenow', progress);
    }

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





