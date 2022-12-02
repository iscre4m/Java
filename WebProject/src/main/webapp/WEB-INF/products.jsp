<%@ page import="java.util.List" %>
<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<Product> products = (ArrayList<Product>) request.getAttribute("products");
    User user = (User) request.getAttribute("user");
%>

<main>
    <ul class="card-list">
        <% for (Product product : products) { %>
        <li class="card-list-item">
            <span><%= product.getName() %></span>
            <span><%= product.getDescription() %></span>
            <span>&dollar;<%= product.getPrice() %></span>
            <% if (user != null) {%>
            <button>Edit</button>
            <form method="post">
                <input type="hidden" value="DELETE" name="method"/>
                <input type="hidden" value="<%= product.getId() %>" name="product-id"/>
                <button type="submit">Delete</button>
            </form>
            <% } %>
        </li>
        <% } %>
    </ul>
</main>