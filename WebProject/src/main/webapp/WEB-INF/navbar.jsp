<% String[] links = {"index", "register"}; %>
<nav>
    <ul>
        <% for (String link : links) { %>
        <li>
            <a href="<%= link %>.jsp">
                <%= link.toUpperCase() %>
            </a>
        </li>
        <% } %>
    </ul>
</nav>