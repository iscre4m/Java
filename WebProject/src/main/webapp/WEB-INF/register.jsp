<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String host = request.getContextPath();
%>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<%= host %>/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
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
<jsp:include page="footer.jsp"/>
</body>
</html>