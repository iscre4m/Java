package step.learning.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String avatar;
    private String email;
    private String emailCode;

    public User() {
    }

    public User(ResultSet result) throws SQLException {
        setId(result.getString("id"));
        setUsername(result.getString("username"));
        setPassword(result.getString("password"));
        setSalt(result.getString("salt"));
        setName(result.getString("name"));
        setAvatar(result.getString("avatar"));
        setEmail(result.getString("email"));
        setEmailCode(result.getString("email_code"));
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
}
