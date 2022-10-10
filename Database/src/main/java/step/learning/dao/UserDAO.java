package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.User;

import java.sql.*;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection;

    @Inject
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts user to database
     *
     * @param user data to insert
     * @return user ID
     */
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        String sqlCommand = "INSERT INTO Users" +
                "(`id`, `username`, `password`, `name`)" +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Insertion error: %s%n", ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            return null;
        }

        return user.getId();
    }

    public boolean isUsernameUnique(String username) {
        String sqlCommand = "SELECT u.username FROM Users as u";

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sqlCommand);
            while(result.next()) {
                if (result.getString("username").equals(username)) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            return false;
        }

        return true;
    }
}