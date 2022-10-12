package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.User;
import step.learning.services.HashService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection;
    private final HashService hashService;

    @Inject
    public UserDAO(Connection connection, HashService hashService) {
        this.connection = connection;
        this.hashService = hashService;
    }

    /**
     * Inserts user to database
     *
     * @param user data to insert
     * @return user ID
     */
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setSalt(hashService.hash(UUID.randomUUID().toString()));
        String sqlCommand = "INSERT INTO Users" +
                "(`id`, `username`, `password`, `salt`, `name`)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, getPasswordHash(user.getPassword(), user.getSalt()));
            statement.setString(4, user.getSalt());
            statement.setString(5, user.getName());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Insertion error: %s%n", ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            return null;
        }

        return user.getId();
    }

    /**
     * Checks if username is unique
     *
     * @param username string to check
     * @return true if login is unique
     */
    public boolean isUsernameUnique(String username) {
        String sqlCommand = "SELECT COUNT(u.id)" +
                "FROM Users AS u WHERE u.`username` = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1) == 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
        }

        return false;
    }

    /**
     * Retrieves user with specified credentials from database
     *
     * @param username credentials: username
     * @param password credentials: password
     * @return entities.User or null if not found
     */
    public User getUserByCredentials(String username, String password) {
        String sqlCommand = "SELECT * FROM Users AS u WHERE u.`username` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = new User(result);

                if (user.getSalt() == null) {
                    return getUserByCredentialsOld(username, password);
                }

                String expectedHash = getPasswordHash(password, user.getSalt());
                if (expectedHash.equals(user.getPassword())) {
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
        }

        return null;
    }

    public User getUserByCredentialsOld(String username, String password) {
        String sqlCommand = "SELECT * FROM users AS u WHERE u.`username` = ? AND u.`password` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, username);
            statement.setString(2, getPasswordHash(password, ""));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new User(result);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
        }

        return null;
    }

    private String getPasswordHash(String password, String salt) {
        return hashService.hash(salt + password + salt);
    }
}