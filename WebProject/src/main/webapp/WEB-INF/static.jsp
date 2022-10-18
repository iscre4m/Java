<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String host = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%= host %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<main>
    <h1>Не удалось подключиться к базе данных</h1>
</main>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>