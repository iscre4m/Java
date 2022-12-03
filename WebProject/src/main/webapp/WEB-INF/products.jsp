<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.common.collect.Multimap" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Multimap<String, Product> usersToProducts = (Multimap<String, Product>) request.getAttribute("usersToProducts");
%>
<main>
    <% if (usersToProducts.size() == 0) { %>
    <h1>No products</h1>
    <% } else { %>
    <ul class="card-list">
        <% for (Map.Entry<String, Product> userToProduct : usersToProducts.entries()) { %>
        <li class="card-list-item">
            <span><%= userToProduct.getValue().getName() %></span>
            <span><%= userToProduct.getValue().getDescription() %></span>
            <span>&dollar;<%= userToProduct.getValue().getPrice() %></span>
            <span>Seller: <%= userToProduct.getKey() %></span>
        </li>
        <% } %>
    </ul>
    <% } %>
</main>