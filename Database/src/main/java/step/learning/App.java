package step.learning;

import com.mysql.cj.jdbc.Driver;

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

//        sqlCommand = String.format("INSERT INTO randoms " +
//                        "VALUES (UUID_SHORT(), %d, '%s')",
//                random.nextInt(),
//                String.format("str %d), random.nextInt());
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(sqlCommand);
//        } catch (SQLException ex) {
//            System.out.printf("Query error: %s%n", ex.getMessage());
//            System.out.printf("SQL Command: %s%n", sqlCommand);
//            return;
//        }
        // endregion

        // region ResultSet
        sqlCommand = "SELECT r.id, r.num, r.str FROM randoms AS r";
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sqlCommand);
            while (result.next()) {
                System.out.printf("%d %11d %s%n",
                        result.getLong("id"),
                        result.getInt("num"),
                        result.getString("str")
                );
            }
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            return;
        }
        // endregion

//        sqlCommand = "INSERT INTO randoms " +
//                "VALUES (UUID_SHORT(), ?, ?)";
//        try(PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
//            statement.setInt(1, random.nextInt());
//            statement.setString(2,
//                    String.format("str %d", random.nextInt()));
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.printf("Query error: %s%n", ex.getMessage());
//            System.out.printf("SQL Command: %s%n", sqlCommand);
//            return;
//        }

        try {
            connection.close();
            DriverManager.deregisterDriver(mysqlDriver);
        } catch (SQLException ignored) {
        }
    }
}