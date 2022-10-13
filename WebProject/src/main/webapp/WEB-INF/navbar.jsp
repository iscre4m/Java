<% String[] links = {"index", "register"}; %>
<nav>
    <ul>
        <% for (String link : links) { %>
        <li>
            <a href="<%= link %>">
                <%= link.toUpperCase() %>
            </a>
        </li>
        <% } %>
    </ul>
</nav>