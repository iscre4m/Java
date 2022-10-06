package step.learning;

import com.mysql.cj.jdbc.Driver;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Random;

public class App {
    private final Random random;

    public App() {
        random = new Random();
    }

    public void run() {
        Driver mysqlDriver;
        try {
            mysqlDriver = new Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException ex) {
            System.out.printf("Driver error: %s%n", ex.getMessage());
            return;
        }

        String connectionString = "jdbc:mysql://localhost:3306/JavaDatabase";
        connectionString += "?useUnicode=true&characterEncoding=UTF-8";
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    connectionString,
                    "restricted_user",
                    "r_user_pass"
            );
        } catch (SQLException ex) {
            System.out.printf("Connection error: %s%n",
                    ex.getMessage());
            return;
        }

        // region executeUpdate
        String sqlCommand = "CREATE TABLE IF NOT EXISTS randoms (" +
                "id              BIGINT      PRIMARY KEY," +
                "num             INT         NOT NULL," +
                "str             VARCHAR(64) NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=UTF8";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            return;
        }

        byte[] bytes = new byte[random.nextInt(10, 21)];
        random.nextBytes(bytes);
        sqlCommand = String.format("INSERT INTO randoms " +
                        "VALUES (UUID_SHORT(), %d, '%s')",
                random.nextInt(),
                new String(bytes, StandardCharsets.UTF_16));
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            return;
        }
        // endregion

        try {
            connection.close();
            DriverManager.deregisterDriver(mysqlDriver);
        } catch (SQLException ignored) {
        }
    }
}