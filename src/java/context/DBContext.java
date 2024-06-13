package context;

import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("context/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String user = prop.getProperty("user");
            String pass = prop.getProperty("password");
            String url = prop.getProperty("url");

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
        }
    }
}