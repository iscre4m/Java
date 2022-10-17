package step.learning.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataService implements DataService {
    private final String connectionString = "jdbc:mysql://localhost:3306/JavaDatabase" +
            "?useUnicode=true&characterEncoding=UTF-8";
    private final String dbUser = "restricted_user";
    private final String dbPass = "r_user_pass";
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