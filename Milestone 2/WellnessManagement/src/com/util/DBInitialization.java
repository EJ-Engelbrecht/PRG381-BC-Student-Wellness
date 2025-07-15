package com.util;

import java.sql.*;

public class DBInitialization {

    private static final String DB_NAME = "WellnessManagement";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "sFicA";
    private static final String DB_HOST_URL = "jdbc:postgresql://localhost:5432/";

    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (initialized) return;

        try {
            // Step 1: Connect to default 'postgres' database
            try (Connection defaultConn = DriverManager.getConnection(DB_HOST_URL + "postgres", DB_USER, DB_PASSWORD)) {
                if (!databaseExists(defaultConn, DB_NAME)) {
                    createDatabase(defaultConn, DB_NAME);
                    System.out.println("✅ Database '" + DB_NAME + "' created.");
                } else {
                    System.out.println("✅ Database '" + DB_NAME + "' already exists.");
                }
            }

            // Step 2: Connect to target database
            try (Connection conn = DriverManager.getConnection(DB_HOST_URL + DB_NAME, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {

                // Create Counselors table
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS counselors (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        specialization VARCHAR(100) NOT NULL,
                        availability VARCHAR(20) NOT NULL
                    );
                """);

                // Create Appointments table
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

                // Create Feedback table
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS feedback (
                        id SERIAL PRIMARY KEY,
                        student VARCHAR(100) NOT NULL,
                        rating INT NOT NULL,
                        comments VARCHAR(255)
                    );
                """);

                System.out.println("✅ All tables checked/created.");
            }

            initialized = true;

        } catch (SQLException e) {
            System.err.println("❌ DB Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean databaseExists(Connection conn, String dbName) throws SQLException {
        String query = "SELECT 1 FROM pg_database WHERE datname = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, dbName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private static void createDatabase(Connection conn, String dbName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE \"" + dbName + "\"");
        }
    }
}
