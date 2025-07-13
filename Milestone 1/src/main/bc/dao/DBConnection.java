
package main.bc.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final  String URL = "jdbc:postgresql://localhost:5432/wellness";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sFicA";


    public static Connection getConnection() throws SQLException {
        System.out.println("DEBUG - Using DB password: " + PASSWORD);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}