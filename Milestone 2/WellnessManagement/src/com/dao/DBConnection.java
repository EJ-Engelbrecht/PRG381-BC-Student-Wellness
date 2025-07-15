package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String PROPERTIES_FILE = "/com/Resources/config.properties";
    private static String URL;

    static {
        try (InputStream input = DBConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load DB config", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("Connected to database");
                return conn;
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}