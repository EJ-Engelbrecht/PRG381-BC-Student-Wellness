package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialization {

    public static void setupDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                "CREATE TABLE counselors (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(100), " +
                    "specialization VARCHAR(100), " +
                    "availability BOOLEAN)"
            );

            stmt.executeUpdate(
                "CREATE TABLE appointments (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "student VARCHAR(100), " +
                    "counselor VARCHAR(100), " +
                    "date DATE, " +
                    "time TIME, " +
                    "status VARCHAR(50))"
            );

            stmt.executeUpdate(
                "CREATE TABLE feedback (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "student VARCHAR(100), " +
                    "rating INT, " +
                    "comments VARCHAR(500))"
            );

            System.out.println("✅ Tables created.");
        } catch (SQLException e) {
            String state = e.getSQLState();
            if ("X0Y32".equals(state)) {
                System.out.println("⚠️ Tables already exist. Skipping creation.");
            } else {
                System.out.println("❌ Error creating tables:");
                e.printStackTrace();
            }
        }
    }
}
