<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String host = request.getContextPath();
    String name = (String) request.getAttribute("name");
    String username = (String) request.getAttribute("username");
%>
<html>
<head>
    <title>Web Project</title>
    <link rel="stylesheet" href="<%= host %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<main>
    <h1>Welcome</h1>
    <% if (name != null && !name.equals("")) {%>
    <p>
        Name: <%= name %>
    </p>
    <% } %>
    <% if (username != null && !username.equals("")) {%>
    <p>
        Username: <%= username %>
    </p>
    <% } %>
</main>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>