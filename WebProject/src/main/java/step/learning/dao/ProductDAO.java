package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.Product;
import step.learning.services.data.DataService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class ProductDAO {
    private final DataService dataService;

    @Inject
    public ProductDAO(DataService dataService) {
        this.dataService = dataService;
    }

    public String add(Product product) {
        product.setId(UUID.randomUUID().toString());
        String command = "INSERT INTO products VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = dataService.getConnection().prepareStatement(command)) {
            statement.setString(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getPrice().toString());

            statement.execute();
        } catch (SQLException ex) {
            System.out.println("ProductDAO::add: " + ex.getMessage());
            System.out.println("Command: " + command);
        }

        return product.getId();
    }
}