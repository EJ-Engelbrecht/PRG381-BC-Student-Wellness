package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialization {

    public static void setupDatabase(Connection conn) {
        System.out.println("⚙️ Setting up database tables...");

        try (Statement stmt = conn.createStatement()) {

            // ---- Counselors Table ----
            try {
                stmt.executeUpdate(
                    "CREATE TABLE counselors (" +
                        "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "name VARCHAR(100), " +
                        "specialization VARCHAR(100), " +
                        "availability BOOLEAN)"
                );
                System.out.println("✅ 'counselors' table created.");
            } catch (SQLException e) {
                if ("X0Y32".equals(e.getSQLState())) {
                    System.out.println("⚠️ 'counselors' table already exists.");
                } else {
                    System.out.println("❌ Error creating 'counselors' table:");
                    e.printStackTrace();
                }
            }

            // ---- Appointments Table ----
            try {
                stmt.executeUpdate(
                    "CREATE TABLE appointments (" +
                        "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "student VARCHAR(100), " +
                        "counselor VARCHAR(100), " +
                        "date DATE, " +
                        "time TIME, " +
                        "status VARCHAR(50))"
                );
                System.out.println("✅ 'appointments' table created.");
            } catch (SQLException e) {
                if ("X0Y32".equals(e.getSQLState())) {
                    System.out.println("⚠️ 'appointments' table already exists.");
                } else {
                    System.out.println("❌ Error creating 'appointments' table:");
                    e.printStackTrace();
                }
            }

            // ---- Feedback Table ----
            try {
                stmt.executeUpdate(
                    "CREATE TABLE feedback (" +
                        "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "student VARCHAR(100), " +
                        "rating INT, " +
                        "comments VARCHAR(500))"
                );
                System.out.println("✅ 'feedback' table created.");
            } catch (SQLException e) {
                if ("X0Y32".equals(e.getSQLState())) {
                    System.out.println("⚠️ 'feedback' table already exists.");
                } else {
                    System.out.println("❌ Error creating 'feedback' table:");
                    e.printStackTrace();
                }
            }

        } catch (SQLException globalErr) {
            System.out.println("❌ Fatal DB setup error:");
            globalErr.printStackTrace();
        }
    }
}
