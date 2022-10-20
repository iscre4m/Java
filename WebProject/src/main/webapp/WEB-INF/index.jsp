<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="step.learning.entities.User" %>
<%
    String name = (String) request.getAttribute("name");
    String username = (String) request.getAttribute("username");
    String authError = (String) request.getAttribute("authError");
    User user = (User) request.getAttribute("user");
%>
<main>
    <% if (authError == null) { %>
    <h1>Welcome</h1>
    <% if (name != null && !name.equals("")) {%>
    <p>
        Name: <%= name %>
    </p>
    <% } %>
    <% if (username != null && !username.equals("")) { %>
    <p>
        Username: <%= username %>
    </p>
    <% } %>
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