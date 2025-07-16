package com.test;

import com.dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ SUCCESS: Connected to the Derby database!");
            } else {
                System.out.println("❌ ERROR: Connection returned null.");
            }
        } catch (SQLException e) {
            System.out.println("❌ SQLException:");
            e.printStackTrace();
        }
    }
}
