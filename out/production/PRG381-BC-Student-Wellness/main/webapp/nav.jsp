<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* Glassy overlay background */
        #navOverlay {
            position: fixed;
            top: 0;
            left: 0;
            height: 100%;
            width: 100%;
            background: rgba(0, 0, 0, 0.2);
            backdrop-filter: blur(1px);
            display: none;
            z-index: 999;
        }

        .sidenav {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1000;
            top: 0;
            left: 0;
            background: rgba(49, 48, 48, 0.5);
            backdrop-filter: blur(1px);
            box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
            overflow-x: hidden;
            transition: 0.3s ease;
            padding-top: 60px;
            border-top-right-radius: 15px;
            border-bottom-right-radius: 15px;
        }

        .sidenav a {
            padding: 15px 25px;
            text-decoration: none;
            font-size: 1.1rem;
            color: #2c3e50;
            display: block;
            transition: 0.3s;
            border-radius: 8px;
            margin: 8px 12px;
        }

        .sidenav a:hover {
            background-color: #111112;
        }
        .sidenav a {
            padding: 15px 25px;
            text-decoration: none;
            font-size: 1.1rem;
            color: #ffffff; /* Changed from #2c3e50 to white */
            display: block;
            transition: 0.3s;
            border-radius: 8px;
            margin: 8px 12px;
        }

        .sidenav .closebtn {
            position: absolute;
            top: 10px;
            right: 18px;
            font-size: 1.5rem;
            color: #ffffff;
            font-weight: bold;
            cursor: pointer;
        }


        .menu-icon {
            position: fixed;
            top: 20px;
            left: 20px;
            padding: 10px 16px;
            font-size: 1.1rem;
            background-color: rgba(0, 0, 0, 0.6);
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            z-index: 1100;
            box-shadow: 0 4px 6px rgba(0,0,0,0.2);
            transition: background-color 0.2s ease;
        }

        .menu-icon:hover {
            background-color: rgba(0, 0, 0, 0.8);
        }
    </style>
</head>
<body>
<nav id="nav">
    <!-- Menu icon -->
    <span class="menu-icon" onclick="openNav()">☰ Menu</span>

    <!-- Overlay -->
    <div id="navOverlay" onclick="closeNav()"></div>

    <!-- Side menu -->
    <div id="Sidenav" class="sidenav">
        <span class="closebtn" onclick="closeNav()">&times;</span>
        <a href="./index.jsp"><span>Home</span></a>
        <a href="./login.jsp"><span>Login</span></a>
        <a href="./register.jsp"><span>Register</span></a>
    </div>
</nav>

<script>
    const sidenav = document.getElementById("Sidenav");
    const overlay = document.getElementById("navOverlay");
    const menuIcon = document.querySelector(".menu-icon");

    function openNav() {
        sidenav.style.width = "220px";
        overlay.style.display = "block";
        menuIcon.style.display = "none";
    }

    function closeNav() {
        sidenav.style.width = "0";
        overlay.style.display = "none";
        menuIcon.style.display = "inline-block";
    }
</script>
</body>
</html>
