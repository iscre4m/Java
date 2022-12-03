<%@ page import="java.util.List" %>
<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<Product> products = (ArrayList<Product>) request.getAttribute("products");
%>

<main>
    <% if (products == null) { %>
    <h1>No products</h1>
    <% } else { %>
    <ul class="card-list">
        <% for (Product product : products) { %>
        <li class="card-list-item">
            <span><%= product.getName() %></span>
            <span><%= product.getDescription() %></span>
            <span>&dollar;<%= product.getPrice() %></span>
        </li>
        <% } %>
    </ul>
    <% } %>
</main>