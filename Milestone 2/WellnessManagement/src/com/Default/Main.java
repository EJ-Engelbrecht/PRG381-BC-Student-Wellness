package com.Default;

import com.dao.DBConnection;
import com.util.DBInitialization;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            // Get a connection first
            Connection conn = DBConnection.getConnection();

            if (conn != null) {
                // Run your table creation logic
                DBInitialization.setupDatabase(conn);
                conn.close();  // Optional: close after use
            } else {
                System.out.println("❌ Connection failed.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error:");
            e.printStackTrace();
        }
    }
}
