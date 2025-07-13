import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {
    private String URL;
    private String USER;
    private String PASSWORD;

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        //loads URL and login details  from .config file
        try (FileInputStream fis = new FileInputStream("./Milestone 1/src/com/Resources/config.properties")) {
            properties.load(fis);

            USER = properties.getProperty("username");
            password = properties.getProperty("password");
            URL = properties.getProperty("url");

            //returns db connection with details entered
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}