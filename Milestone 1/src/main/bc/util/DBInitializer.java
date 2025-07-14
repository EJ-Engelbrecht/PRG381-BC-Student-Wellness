package main.bc.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import main.bc.dao.DBConnection;

public class DBInitializer {

    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (initialized) return;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    student_number VARCHAR(20) UNIQUE NOT NULL,
                    name VARCHAR(100) NOT NULL,
                    surname VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    phone VARCHAR(20),
                    password VARCHAR(255) NOT NULL,
                    session_token VARCHAR(255)
                );
            """;

            stmt.executeUpdate(createUsersTable);
            System.out.println("DEBUG - Users table checked/created.");
            initialized = true;

        } catch (SQLException e) {
            System.err.println("ERROR - Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
