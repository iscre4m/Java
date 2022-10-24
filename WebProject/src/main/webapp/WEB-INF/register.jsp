<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String regError = (String) request.getAttribute("regError");
    String username = (String) request.getAttribute("username");
    String name = (String) request.getAttribute("name");
%>
<div>
    <form method="post" enctype="multipart/form-data">
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
        <button type="submit">Register</button>
    </form>
</div>