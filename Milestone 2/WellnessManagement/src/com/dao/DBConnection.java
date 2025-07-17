package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static String URL = "jdbc:derby:wellnessDB;create=true";


    //creates and returns a connection object with the database URL
    public static Connection getConnection() {
        try {
            // Load the Apache Derby driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Return the connection
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            System.out.println("Derby driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        return null;
    }
}
