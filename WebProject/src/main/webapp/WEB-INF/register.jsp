<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String regError = (String) request.getAttribute("regError");
    String username = (String) request.getAttribute("username");
    String name = (String) request.getAttribute("name");
    String email = (String) request.getAttribute("email");
%>
<main>
    <form method="post" enctype="multipart/form-data" class="auth-form">
        <% if (regError != null) { %>
        <h4>
            <%= regError %>
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
        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword">
        </div>
        <div>
            <label for="name">Name</label>
            <input type="text" id="name" name="name" value="<%= name == null ? "" : name %>">
        </div>
        <div>
            <label for="avatar">Avatar</label>
            <input type="file" id="avatar" name="avatar"/>
        </div>
        <div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="<%= email == null ? "" : email %>"/>
        </div>
        <button type="submit">Register</button>
    </form>
</main>