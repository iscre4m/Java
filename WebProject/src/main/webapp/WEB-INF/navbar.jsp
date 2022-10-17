<% String[] links = {"index", "register", "filter"}; %>
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