<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="step.learning.entities.User" %>
<%
    String[] links = {"filter", "hash"};
    User user = (User) request.getAttribute("user");
%>
<nav>
    <ul>
        <li>
            <a href="<%= request.getContextPath() %>">
                HOME
            </a>
        </li>
        <% for (String link : links) { %>
        <li>
            <a href="<%= link %>">
                <%= link.toUpperCase() %>
            </a>
        </li>
        <% } %>
    </ul>
    <ul>
        <% if (user == null) { %>
        <li>
            <a href="register">REGISTER</a>
        </li>
        <li>
            <a href="login">LOGIN</a>
        </li>
        <% } else { %>
        <li>
            <form>
                <input type="hidden" name="logout" value="navbar-logout"/>
                <button type="submit">
                    LOGOUT
                </button>
            </form>
        </li>
        <% } %>
    </ul>
</nav>