package step.learning.services.data;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class MySQLDataService implements DataService {
    private Connection connection;

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/java_database" +
                                "?useUnicode=true&characterEncoding=UTF-8",
                        "db_user", "123"
                );
            } catch (Exception ex) {
                System.out.println("Connection error: " + ex.getMessage());
            }
        }

        return connection;
    }
}