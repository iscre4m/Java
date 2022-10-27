package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import step.learning.entities.User;
import step.learning.services.data.DataService;
import step.learning.services.hash.HashService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection;
    private final HashService hashService;
    private final DataService dataService;

    @Inject
    public UserDAO(DataService dataService,
                   @Named("SHA1HashService") HashService hashService) {
        this.dataService = dataService;
        this.hashService = hashService;

        this.connection = dataService.getConnection();
    }

    /**
     * Inserts user to database
     *
     * @param user to insert
     * @return user ID
     */
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setSalt(hashService.hash(UUID.randomUUID().toString()));
        String sqlCommand = "INSERT INTO users" +
                "(`id`, `username`, `password`, `salt`, `name`, `avatar`)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, getPasswordHash(user.getPassword(), user.getSalt()));
            statement.setString(4, user.getSalt());
            statement.setString(5, user.getName());
            statement.setString(6, user.getAvatar());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Insertion error: %s%n", ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            return null;
        }

        return user.getId();
    }

    /**
     * Updates user data that are not null fields in 'editedUser' parameter
     *
     * @param editedUser with id and fields to update
     * @return true if updated successfully
     */
    public boolean update(User editedUser) {
        if (editedUser == null || editedUser.getId() == null) {
            return false;
        }
        Map<String, String> dataToEdit = new HashMap<>();
        String name = editedUser.getName();
        String username = editedUser.getUsername();
        String avatar = editedUser.getAvatar();

        if (name != null) {
            dataToEdit.put("name", name);
        }
        if (username != null) {
            if (isUsernameUnique(username)) {
                dataToEdit.put("username", username);
            }
        }
        if (avatar != null) {
            dataToEdit.put("avatar", avatar);
        }
        if (dataToEdit.isEmpty()) {
            return false;
        }

        StringBuilder sqlCommandBuilder = new StringBuilder("UPDATE users AS u SET ");
        boolean commaRequired = false;
        for (String fieldName : dataToEdit.keySet()) {
            sqlCommandBuilder.append(String.format("%c u.`%s` = ?",
                    (commaRequired ? ',' : ' '), fieldName)
            );
            commaRequired = true;
        }
        sqlCommandBuilder.append("WHERE u.`id` = ?");

        try (PreparedStatement statement = connection.prepareStatement(sqlCommandBuilder.toString())) {
            int pos = 1;
            for (String fieldName : dataToEdit.keySet()) {
                statement.setString(pos, dataToEdit.get(fieldName));
                ++pos;
            }
            statement.setString(pos, editedUser.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Update error: " + ex.getMessage());
            System.out.println("Command: " + sqlCommandBuilder);
            return false;
        }

        return true;
    }

    /**
     * Checks if username is unique
     *
     * @param username to check
     * @return true if login is unique
     */
    public boolean isUsernameUnique(String username) {
        String sqlCommand = "SELECT COUNT(u.id)" +
                "FROM users AS u WHERE u.`username` = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1) == 0;
            }
        } catch (SQLException ex) {
            System.out.println("UserDAO::isUsernameUnique(): " + ex.getMessage());
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
        String sqlCommand = "SELECT * FROM users AS u WHERE u.`username` = ?";
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
            System.out.println("UserDAO::getUserByCredentials(): " + ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
        }

        return null;
    }

    /**
     * Retrieves user with specified id from database
     *
     * @param userId in database
     * @return user as entity or null
     */
    public User getUserById(String userId) {
        String sqlCommand = "SELECT u.* FROM users AS u " +
                "WHERE u.`id`= ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new User(result);
            }
        } catch (SQLException ex) {
            System.out.println("UserDAO::getUserById(): " + ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            System.out.println("User ID: " + userId);
        }

        return null;
    }

    public User getUserByCredentialsOld(String username, String password) {
        String sqlCommand = "SELECT * FROM users AS u " +
                "WHERE u.`username` = ? AND u.`password` = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, username);
            statement.setString(2, getPasswordHash(password, ""));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new User(result);
            }
        } catch (SQLException ex) {
            System.out.println("UserDAO::getUserByCredentialsOld(): " + ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
        }

        return null;
    }

    private String getPasswordHash(String password, String salt) {
        return hashService.hash(salt + password + salt);
    }
}