<%
    String[] links = {"home", "filter", "hash"};
    String userId = (String) request.getAttribute("userId");
%>
<nav>
    <ul>
        <% for (String link : links) { %>
        <li>
            <a href="
                <% if (link.equals("home")) { %>
                <%= request.getContextPath() %>
                <% } else { %> <%= link %>
                <% } %>">
                <%= link.toUpperCase() %>
            </a>
        </li>
        <% } %>
    </ul>
    <ul>
        <% if (userId == null) { %>
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