package step.learning.services;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class MySQLDataService implements DataService {
    private final String connectionString = "jdbc:mysql://localhost:3306/javadatabase" +
            "?useUnicode=true&characterEncoding=UTF-8";
    private final String dbUser = "root";
    private final String dbPass = "";
    private Connection connection;

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            } catch (Exception ex) {
                System.out.println("Connection error: " + ex.getMessage());
            }
        }

        return connection;
    }
}