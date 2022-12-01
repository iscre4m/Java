<%@ page import="java.util.List" %>
<%@ page import="step.learning.entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<Product> products = (ArrayList<Product>) request.getAttribute("products");
%>

<main>
    <ul>
        <% for (Product product : products) { %>
        <li>
            <%= product.getName() %>
        </li>
        <% } %>
    </ul>
</main>