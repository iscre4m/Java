package step.learning;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import step.learning.services.HashService;
import step.learning.services.SHA1HashService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigModule extends AbstractModule {
    private final String connectionString = "jdbc:mysql://localhost:3306/JavaDatabase?" +
            "useUnicode=true&characterEncoding=UTF-8";

    @Override
    protected void configure() {
        bind(HashService.class).to(SHA1HashService.class);
        bind(String.class)
                .annotatedWith(Names.named("MySQLConnectionString"))
                .toInstance(connectionString);
    }

    Connection connection;
    @Provides
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString,
                    "restricted_user", "r_user_pass");
        }

        return connection;
    }
}