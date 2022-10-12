<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="WEB-INF/navbar.jsp"/>
<div>
    <form>
        <div>
            <label for="username">Username</label>
            <input type="text" id="username">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password">
        </div>
        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword">
        </div>
        <div>
            <label for="name">Name</label>
            <input type="text" id="name">
        </div>
        <button type="submit">Register</button>
    </form>
</div>
<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>