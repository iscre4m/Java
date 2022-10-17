<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String message = (String) request.getAttribute("from-filter");
%>
<html>
<head>
    <title>Filters</title>
</head>
<body>
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
</body>
</html>