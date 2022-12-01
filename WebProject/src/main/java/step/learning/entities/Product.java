package step.learning.entities;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product() {}

    public Product(ResultSet result) throws SQLException {
        setId(result.getString("id"));
        setName(result.getString("name"));
        setDescription(result.getString("description"));
        setPrice(new BigDecimal(result.getString("price")));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
