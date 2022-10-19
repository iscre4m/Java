package step.learning.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String id;
    private String username;
    private String password;
    private String salt;
    private String name;

    public User() {
    }

    public User(ResultSet result) throws SQLException {
        setId(result.getString("id"));
        setUsername(result.getString("username"));
        setPassword(result.getString("password"));
        setSalt(result.getString("salt"));
        setName(result.getString("name"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
