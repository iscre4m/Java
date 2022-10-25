<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="step.learning.entities.User" %>
<%
    String authError = (String) request.getAttribute("authError");
    User user = (User) request.getAttribute("user");
%>
<main>
    <% if (authError == null) { %>
    <h1>Welcome</h1>
    <% if (user != null) { %>
    <p>
        Username: <%= user.getUsername() %>
    </p>
    <p>
        Name: <%= user.getName() %>
    </p>
    <% } %>
    <% } else { %>
    <h1>
        <%= authError %>
    </h1>
    <% } %>
</main>