<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String host = request.getContextPath();
    String message = (String) request.getAttribute("from-filter");
    String[] usernames = (String[]) request.getAttribute("usernames");
%>
<html>
<head>
    <title>Filters</title>
    <link rel="stylesheet" href="<%= host %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<main>
<h1>Сервлетные фильтры</h1>
<p>
    <%= (String) request.getCharacterEncoding() %>
</p>
<% if (message == null) { %>
<p>Нет атрибута</p>
<% } else { %>
<p>
    <%= message %>
</p>
<% } %>
<% if(usernames != null) { %>
<ul>
    <% for (String username : usernames) { %>
    <li><%= username %></li>
    <% } %>
</ul>
<% } %>
</main>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>