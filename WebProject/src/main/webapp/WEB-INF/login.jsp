<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <form method="post">
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password">
        </div>
        <input type="hidden" name="auth-form" value="navbar-auth-form"/>
        <button type="submit">Login</button>
    </form>
</div>