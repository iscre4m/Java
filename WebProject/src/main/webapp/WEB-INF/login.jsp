<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String authError = (String) request.getAttribute("authError");
    String username = (String) request.getAttribute("username");
%>
<main>
    <form method="post" class="auth-form">
        <% if (authError != null) { %>
        <h4>
            <%= authError %>
        </h4>
        <% } %>
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username" value="<%= username == null ? "" : username %>">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password">
        </div>
        <input type="hidden" name="auth-form" value="auth-form"/>
        <button type="submit">Login</button>
    </form>
</main>