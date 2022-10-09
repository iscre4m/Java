package step.learning;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Date;
import java.util.Random;

public class App {
    private final Random random;
    private Connection connection;

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

        try {
            connection = DriverManager.getConnection(connectionString,
                    "restricted_user", "r_user_pass");
        } catch (SQLException ex) {
            System.out.printf("Connection error: %s%n", ex.getMessage());
            return;
        }

        createTableRandoms();
        //insertRandomValuesIntoRandoms();
        //printAllRecordsInRandoms();

        createTableRandoms2();
        insertRandomValuesIntoRandoms2();
        printAllRecordsInRandoms2();

        try {
            connection.close();
            DriverManager.deregisterDriver(mysqlDriver);
        } catch (SQLException ignored) {
        }
    }

    private void createTableRandoms() {
        String sqlCommand = "     CREATE TABLE IF NOT EXISTS randoms (" +
                        " id                  BIGINT      PRIMARY KEY," +
                        "num                     INT         NOT NULL," +
                        "str             VARCHAR(64)             NULL)" +
                             " ENGINE=InnoDB DEFAULT CHARSET=UTF8";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
    }

    private void insertRandomValuesIntoRandoms() {
        // region raw
        String sqlCommand = String.format("INSERT INTO randoms " +
                "VALUES (UUID_SHORT(), %d, '%s')",
                random.nextInt(), String.format("str %d", random.nextInt()));
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
        // endregion
        // region prepared
        sqlCommand = "INSERT INTO randoms " + "VALUES (UUID_SHORT(), ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setInt(1, random.nextInt());
            statement.setString(2, String.format("str %d", random.nextInt()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
        // endregion
    }

    private void printAllRecordsInRandoms() {
        String sqlCommand = "SELECT r.id, r.num, r.str FROM randoms AS r";
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sqlCommand);
            while (result.next()) {
                System.out.printf("%d %11d %s%n",
                        result.getLong("id"),
                        result.getInt("num"),
                        result.getString("str"));
            }
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
    }

    private void createTableRandoms2() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS randoms2 (" + "            id      BIGINT UNSIGNED PRIMARY KEY DEFAULT     uuid_short()," + "      intValue                  INT    NOT NULL DEFAULT                0," + "   stringValue          VARCHAR(64)    NOT NULL DEFAULT    'str default'," + "    floatValue                FLOAT    NOT NULL DEFAULT              0.0," + "timestampValue            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP" + ") ENGINE=InnoDB DEFAULT CHARSET=UTF8";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
    }

    private void insertRandomValuesIntoRandoms2() {
        String sqlCommand = "INSERT INTO randoms2 " + "VALUES (UUID_SHORT(), ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setInt(1, random.nextInt());
            statement.setString(2, String.format("str %d", random.nextInt()));
            statement.setFloat(3, random.nextFloat());
            statement.setTimestamp(4, new Timestamp(
                    random.nextLong(0, new Date().getTime())));
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Query error: %s", ex.getMessage());
            System.out.printf("Command: %s", sqlCommand);
        }
    }

    private void printAllRecordsInRandoms2() {
        String sqlCommand = "SELECT              r.id," +
                                          "r.intValue," +
                                       "r.stringValue," +
                                        "r.floatValue," +
                                    "r.timestampValue " +
                                    "FROM randoms2 AS r";
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sqlCommand);
            while (result.next()) {
                System.out.printf("%d - %11d - %15s - %f - %s%n",
                        result.getLong("id"),
                        result.getInt("intValue"),
                        result.getString("stringValue"),
                        result.getFloat("floatValue"),
                        result.getTimestamp("timestampValue"));
            }
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
    }
}