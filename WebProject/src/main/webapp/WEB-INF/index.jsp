<%@ page contentType="text/html;charset=UTF-8" %>
<% String username = (String) request.getAttribute("username"); %>
<html>
<head>
    <title>Web Project</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main>
    <h1>
        Welcome
        <% if (username != null) { %>
        <%= username %>
        <% } %>
    </h1>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>