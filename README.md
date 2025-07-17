# 🛠️ BC Wellness Web App - Setup Guide (PostgreSQL + Tomcat + IntelliJ)

---

# Milestone 1

## ✅ 1. Install PostgreSQL 15.13

🔗 [Download PostgreSQL 15.13](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

### During Installation:
- ✅ Install **all components** when prompted
- ✅ Set the password to: `sFicA` (easy to remember)
- ✅ Leave the port as **5432**
- ✅ Leave the locale as **DEFAULT**
- ✅ You **don’t need StackBuilder** if you already have **pgAdmin 4**

---

## ✅ 2. Install pgAdmin 4

> If not already installed, get it from the PostgreSQL installer or install separately.

After installing:
1. Press `Win + S` → search for **pgAdmin4**
2. Connect using:
    - Username: `postgres`
    - Password: `sFicA`

---

## ✅ 3. Install Apache Tomcat 9.0

🔗 [Download Tomcat 9.0 - Windows Service Installer](https://tomcat.apache.org/download-90.cgi)

> Scroll to **Core** and download:
**"32-bit/64-bit Windows Service Installer"**

### During Installation:
- ✅ Install **all components** when asked
- ✅ Leave configuration settings as **default**
- ✅ Choose an install location or keep default:  
  `C:\Program Files\Apache Software Foundation\Tomcat 9.0`
- ❌ Uncheck the option to run tomcat.
- ❌ Uncheck the option to view the README
---

## ✅ 4. Add Tomcat to Environment Variables

1. Press `Win + S` → Search for **Edit the system environment variables**
2. In the new window, click **Environment Variables**
3. Under **User Variables**, select **Path** → click **Edit**
4. Click **New** and add:

```
C:\Program Files\Apache Software Foundation\Tomcat 9.0
```

5. Click **OK**, then **OK**, and again **OK**

---

## ✅ 5. Add PostgreSQL JDBC Driver to Tomcat

🔗 [Download JDBC JAR - postgresql-42.7.3.jar](https://jdbc.postgresql.org/download/)

1. Download `postgresql-42.7.3.jar`
2. Paste it into:
```
C:\Program Files\Apache Software Foundation\Tomcat 9.0\lib
```

---

## ✅ 6. Set Up Tomcat in IntelliJ

1. In IntelliJ: `File → Run → Edit Configurations`
2. Press the `+` → **Tomcat Server > Local**
3. Name it: `bcwellness`
4. Under **Application Server**, click **Configure**
    - Locate and select your Tomcat install folder:
      ```
      C:\Program Files\Apache Software Foundation\Tomcat 9.0
      ```

### Deployment Tab:
1. Go to **Deployment** tab
2. Click `+` → **Artifact**
3. Choose:
   ```
   PRG381-BC-Student-Wellness:war exploded
   ```
4. Click **Apply**, then **OK**

---

## ✅ 7. Run the Web App

1. Click the **green play button** in the top-right of IntelliJ
2. This will launch Tomcat and open your app in a browser

---

## ✅ Troubleshooting Tips

- **Error: "relation 'users' does not exist"**  
  → Make sure the `users` table was created in the `wellness` database.

- **500 Internal Server Error**  
  → Check IntelliJ's terminal/logs for stack traces.

- **Can’t connect to DB?**
    - Verify:
        - Correct JDBC URL: `jdbc:postgresql://localhost:5432/wellness`
        - Username: `postgres`
        - Password: `sFicA`

# 📘 PRG381 Milestone 2 – BC Student Wellness Management System

This is a **Java desktop application** for managing student wellness services at Belgium Campus. Built with **Java Swing**, **JavaDB**, and designed using **OOP principles and MVC architecture**, the system provides counselors, students, and administrators with tools to manage appointments, counselor details, and student feedback.

---

# 🔧 Apache Derby (JavaDB) download guide – Embedded Mode

1. Go to the official Derby website:  
   👉 https://db.apache.org/derby/derby_downloads.html

2. Download the **Binary Distribution** ZIP (e.g., `db-derby-10.17.1.0-bin.zip`)

3. Extract it to a folder such as: C:\src\Java DB\db-derby-10.17.1.0-bin  

---

## ✅ Key Features

- **Appointment Management**
  - Book, view, update, and cancel appointments
  - Select counselor, date, time, and set status

- **Counselor Management**
  - Add, view, update, and remove counselors
  - Manage specialization and availability

- **Feedback Management**
  - Students can rate and leave feedback
  - View, edit, and delete feedback entries

- **User Interface**
  - Built with **Java Swing**
  - Uses tabbed panes and/or navigation menus
  - Input validation and confirmation dialogs
  - Exception handling for DB operations

---

## 🧠 Technologies Used

- Java SE 21  
- Java Swing  
- JavaDB (Derby)  
- MVC Pattern  
- Git & GitHub for version control

---

## 🔧 How to Run

1. Open the project in **NetBeans**.
2. Ensure **JavaDB** is configured and running.
3. Run the main class in the base package to launch the application dashboard.
