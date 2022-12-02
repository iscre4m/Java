<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Product product = (Product) request.getAttribute("product");
    String id = null;
    String name = null;
    String description = null;
    BigDecimal price = null;
    if (product != null) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
    }
%>
<main>
    <form method="post">
        <input type="hidden" value="<%= product == null ? "POST" : "PUT" %>" name="method"/>
        <input type="hidden" value="<%= id == null ? "" : id %>" name="product-id"/>
        <div>
            <label for="name">Name</label>
            <input type="text" id="name" name="name"
                   value="<%= name == null ? "" : name %>"/>
        </div>
        <div>
            <label for="description">Description</label>
            <input type="text" id="description" name="description"
                   value="<%= description == null ? "" : description %>"/>
        </div>
        <div>
            <label for="price">Price</label>
            <input type="text" id="price" name="price"
                   value="<%= price == null ? "" : price %>"/>
        </div>
        <button type="submit">
            <%= product == null ? "Add" : "Edit" %>
        </button>
    </form>
</main>