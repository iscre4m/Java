<% String[] links = {"home", "register", "filter", "hash"}; %>
<nav>
    <ul>
        <% for (String link : links) { %>
        <li>
            <a href="<% if(link.equals("home")) { %>
                <%= request.getContextPath() %>
                <% } else { %> <%= link %>
                <% } %>">
                <%= link.toUpperCase() %>
            </a>
        </li>
        <% } %>
    </ul>
</nav>