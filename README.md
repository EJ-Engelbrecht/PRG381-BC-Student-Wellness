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

## ✅ 3. Create the Database & Table

### In pgAdmin:
1. Expand **Servers > PostgreSQL 15**
2. Right-click **Databases > Create > Database**
    - Name it: `wellness`
    - Click **Save**

### Then:
1. Select `wellness`
2. Click the **Query Tool**
3. Paste and run the following SQL:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    session_token VARCHAR(255)
);
```

✅ Press the **Play button** (or `F5`) to execute.

---

## ✅ 4. Install Apache Tomcat 9.0

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

## ✅ 5. Add Tomcat to Environment Variables

1. Press `Win + S` → Search for **Edit the system environment variables**
2. In the new window, click **Environment Variables**
3. Under **User Variables**, select **Path** → click **Edit**
4. Click **New** and add:

```
C:\Program Files\Apache Software Foundation\Tomcat 9.0
```

5. Click **OK**, then **OK**, and again **OK**

---

## ✅ 6. Add PostgreSQL JDBC Driver to Tomcat

🔗 [Download JDBC JAR - postgresql-42.7.3.jar](https://jdbc.postgresql.org/download/)

1. Download `postgresql-42.7.3.jar`
2. Paste it into:
```
C:\Program Files\Apache Software Foundation\Tomcat 9.0\lib
```

---

## ✅ 7. Set Up Tomcat in IntelliJ

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

## ✅ 8. Run the Web App

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
