<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String id = (String) request.getAttribute("id");
    String buttonText = (String) request.getAttribute("buttonText");
    String name = (String) request.getAttribute("name");
    String description = (String) request.getAttribute("description");
    String priceString = (String) request.getAttribute("priceString");
    BigDecimal price = (BigDecimal) request.getAttribute("price");

    Boolean nameIsBlank = (Boolean) request.getAttribute("nameIsBlank");
    Boolean descriptionIsBlank = (Boolean) request.getAttribute("descriptionIsBlank");
    Boolean priceIsBlank = (Boolean) request.getAttribute("priceIsBlank");
    Boolean priceIsInvalid = (Boolean) request.getAttribute("priceIsInvalid");
%>
<main>
    <form method="post">
        <input type="hidden" value="<%= id == null ? "POST" : "PUT" %>" name="method"/>
        <input type="hidden" value="<%= id == null ? "" : id %>" name="product-id"/>
        <div>
            <% if (nameIsBlank != null) { %>
            <span style="color: red">Name can't be empty</span>
            <% } %>
            <label for="name">Name</label>
            <input type="text" id="name" name="name"
                   value="<%= name == null ? "" : name %>"/>
        </div>
        <div>
            <% if (descriptionIsBlank != null) { %>
            <span style="color: red">Description can't be empty</span>
            <% } %>
            <label for="description">Description</label>
            <input type="text" id="description" name="description"
                   value="<%= description == null ? "" : description %>"/>
        </div>
        <div>
            <% if (priceIsBlank != null) { %>
            <span style="color: red">Price can't be empty</span>
            <% } %>
            <% if (priceIsInvalid != null) { %>
            <span style="color: red">Invalid price</span>
            <% } %>
            <label for="price">Price</label>
            <input type="text" id="price" name="price"
                   value="<%= priceString == null ? price == null ? "" : price : priceString %>"/>
        </div>
        <button type="submit">
            <%= buttonText %>
        </button>
    </form>
</main>