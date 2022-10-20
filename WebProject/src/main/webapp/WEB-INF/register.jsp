<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String regError = (String) request.getAttribute("regError");
%>
<div>
    <form method="post">
        <% if (regError != null) { %>
        <h4>
            <%= regError %>
        </h4>
        <% } %>
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password">
        </div>
        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword">
        </div>
        <div>
            <label for="name">Name</label>
            <input type="text" id="name" name="name">
        </div>
        <button type="submit">Register</button>
    </form>
</div>