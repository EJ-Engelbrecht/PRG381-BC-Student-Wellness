package com.util;

import java.sql.*;
import com.dao.DBConnection;

public class DBInitialization {

    private static boolean initialized = false;

    //Attempts to connect with the database, and create the relevant tables if they do not exist yet
    public static synchronized void initialize() {
        if (initialized) return;

        DBConnection dbcon = new DBConnection();

            // Connect to target database
            try (Connection conn = dbcon.getConnection();
                 Statement stmt = conn.createStatement()) {

                // Create Counselors table
                try {
                    stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS counselors (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        specialization VARCHAR(100) NOT NULL,
                        availability VARCHAR(20) NOT NULL
                    );
                    """);
                } catch (SQLException e) {
                    if (!"X0Y32".equals(e.getSQLState())) { // Table already exists
                        throw e;
                    }
                }

                // Create Appointments table
                try {
                    stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS appointments (
                        id SERIAL PRIMARY KEY,
                        student VARCHAR(100) NOT NULL,
                        counselor VARCHAR(100) NOT NULL,
                        date VARCHAR(20) NOT NULL,
                        time VARCHAR(10) NOT NULL,
                        status VARCHAR(20) NOT NULL
                    );
                    """);
                } catch (SQLException e) {
                    if (!"X0Y32".equals(e.getSQLState())) { // Table already exists
                        throw e;
                    }
                }

                // Create Feedback table
                try {
                    stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS feedback (
                        id SERIAL PRIMARY KEY,
                        student VARCHAR(100) NOT NULL,
                        rating INT NOT NULL,
                        comments VARCHAR(255)
                    );
                    """);
                } catch (SQLException e) {
                    if (!"X0Y32".equals(e.getSQLState())) { // Table already exists
                        throw e;
                    }
                }

                System.out.println("✅ All tables checked/created.");
            }

            initialized = true;

        } catch (SQLException e) {
            System.err.println("❌ DB Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }

}
