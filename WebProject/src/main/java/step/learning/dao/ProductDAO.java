package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.Product;
import step.learning.services.data.DataService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            statement.setBigDecimal(4, product.getPrice());

            statement.execute();
        } catch (SQLException ex) {
            System.out.println("ProductDAO.add: " + ex.getMessage());
            System.out.println("Command: " + command);
        }

        return product.getId();
    }

    public List<Product> getAll() {
        List<Product> resultList = new ArrayList<>();
        String command = "SELECT * FROM products";

        try (Statement statement = dataService.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(command);

            while (resultSet.next()) {
                resultList.add(new Product(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("ProductDAO.getAll: " + ex.getMessage());
            System.out.println("Command: " + command);
        }

        return resultList;
    }

    public void removeById(String id) {
        String command = "DELETE FROM products WHERE `id` = ?";

        try (PreparedStatement statement = dataService.getConnection().prepareStatement(command)) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("ProductDAO.removeById" + ex.getMessage());
            System.out.println("Command: " + command);
        }
    }

    public Product getById(String id) {
        Product product = null;
        String command = "SELECT * FROM products WHERE `id` = ?";

        try (PreparedStatement statement = dataService.getConnection().prepareStatement(command)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                product = new Product(result);
            }
        } catch (SQLException ex) {
            System.out.println("ProductDAO.getById: " + ex.getMessage());
            System.out.println("Command: " + command);
        }

        return product;
    }

    public void update(Product product) {
        String command = "" +
                "UPDATE products " +
                "SET `name` = ?, `description` = ?, `price` = ? " +
                "WHERE `id` = ?";

        try (PreparedStatement statement = dataService.getConnection().prepareStatement(command)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setString(4, product.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ProductDAO.update: " + ex.getMessage());
            System.out.println("Command: " + command);
        }
    }
}