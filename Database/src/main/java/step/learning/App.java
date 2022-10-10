package step.learning;

import com.google.inject.Inject;
import com.mysql.cj.jdbc.Driver;
import step.learning.dao.UserDAO;
import step.learning.entities.User;
import step.learning.services.HashService;

import javax.inject.Named;
import java.sql.*;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class App {
    private final Random random;
    private final HashService hashService;
    private final String connectionString;
    private Connection connection;
    private final UserDAO userDAO;
    private final Scanner kbScanner;

    @Inject
    public App(HashService hashService,
               @Named("MySQLConnectionString") String connectionString,
               Connection connection,
               UserDAO userDAO) {
        random = new Random();
        this.hashService = hashService;
        this.connectionString = connectionString;
        this.connection = connection;
        this.userDAO = userDAO;
        kbScanner = new Scanner(System.in);
    }

    public void run() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS Users (" +
                "      `id`     CHAR(36) DEFAULT UUID() COMMENT 'UUID'," +
                "`username`  VARCHAR(64) NOT NULL," +
                "`password`     CHAR(40) NOT NULL       COMMENT 'SHA-160 hash'," +
                "    `name` VARCHAR(128) NOT NULL," +
                "PRIMARY KEY(id))  ENGINE=InnoDB  DEFAULT CHARSET=UTF8";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Creation error: %s", ex.getMessage());
            System.out.printf("Command: %s", sqlCommand);
        }

        System.out.println("1 - Register");
        System.out.println("2 - Login");
        System.out.print("Input choice: ");
        int userInput = kbScanner.nextInt();
        kbScanner.nextLine();
        if (userInput == 1) {
            String username = getStringInput("username");
            while (!userDAO.isUsernameUnique(username)) {
                System.out.printf("Username '%s' already exists%n", username);
                username = getStringInput("username");
            }
            String password = getStringInput("password");
            String name = getStringInput("name");

            User user = new User();
            user.setUsername(username);
            user.setPassword(hashService.hash(password));
            user.setName(name);

            if (userDAO.add(user) == null) {
                System.out.println("Insertion failed");
            } else {
                System.out.println("Insertion successful");
            }
        }
    }

    private String getStringInput(String parameter) {
        String input;

        while (true) {
            System.out.printf("Input %s: ", parameter);
            input = kbScanner.nextLine();
            if (input.equals("")) {
                System.out.printf("Empty %s is not allowed%n", parameter);
                continue;
            }
            break;
        }

        return input;
    }

    public void run2() {
        Driver mysqlDriver;
        try {
            mysqlDriver = new Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException ex) {
            System.out.printf("Driver error: %s%n", ex.getMessage());
            return;
        }

        try {
            connection = DriverManager.getConnection(connectionString,
                    "restricted_user", "r_user_pass");
        } catch (SQLException ex) {
            System.out.printf("Connection error: %s%n", ex.getMessage());
            return;
        }

        createTableRandoms();
//        insertRandomValuesIntoRandoms();
//        printAllRecordsInRandoms();

        createTableRandoms2();
        //insertRandomValuesIntoRandoms2();
        //printAllRecordsInRandoms2();

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
        String sqlCommand = "CREATE TABLE IF NOT EXISTS randoms2 (" +
                "            id             CHAR(36) PRIMARY KEY DEFAULT           uuid()," +
                "      intValue                  INT    NOT NULL DEFAULT                0," +
                "   stringValue          VARCHAR(64)    NOT NULL DEFAULT    'str default'," +
                "    floatValue                FLOAT    NOT NULL DEFAULT              0.0," +
                "timestampValue            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ") ENGINE=InnoDB DEFAULT CHARSET=UTF8";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            System.out.printf("Query error: %s%n", ex.getMessage());
            System.out.printf("SQL Command: %s%n", sqlCommand);
            System.exit(0);
        }
    }

    private void insertRandomValuesIntoRandoms2() {
        String sqlCommand = "INSERT INTO randoms2 " + "VALUES (UUID(), ?, ?, ?, ?)";

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
        String sqlCommand = "SELECT " +
                "r.id," +
                "r.intValue," +
                "r.stringValue," +
                "r.floatValue," +
                "r.timestampValue " +
                "FROM randoms2 AS r";
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sqlCommand);
            while (result.next()) {
                System.out.printf("%s - %11d - %15s - %f - %s%n",
                        result.getString("id"),
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