<%@ page import="java.util.List" %>
<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<Product> products = (ArrayList<Product>) request.getAttribute("products");
%>
<main>
    <% if (products == null) { %>
    <h1>No products</h1>
    <% } else { %>
    <form method="post">
        <input type="hidden" value="POST" name="method"/>
        <button type="submit">Add product</button>
    </form>
    <ul class="card-list">
        <% for (Product product : products) { %>
        <li class="card-list-item">
            <span><%= product.getName() %></span>
            <span><%= product.getDescription() %></span>
            <span>&dollar;<%= product.getPrice() %></span>
            <form method="post">
                <input type="hidden" value="PUT" name="method"/>
                <input type="hidden" value="<%= product.getId() %>" name="product-id"/>
                <button type="submit">Edit</button>
            </form>
            <form method="post">
                <input type="hidden" value="DELETE" name="method"/>
                <input type="hidden" value="<%= product.getId() %>" name="product-id"/>
                <button type="submit">Delete</button>
            </form>
        </li>
        <% } %>
    </ul>
    <% } %>
</main>