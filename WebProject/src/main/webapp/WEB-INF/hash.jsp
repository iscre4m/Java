<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
    Boolean emptyData = (Boolean) request.getAttribute("emptyData");
    String md5Hash = (String) request.getAttribute("md5Hash");
    String sha1Hash = (String) request.getAttribute("sha1Hash");
%>
<html>
<head>
    <title>Hash</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp"/>
<main>
    <% if (emptyData != null && emptyData) { %>
    <p>
        No data to hash
    </p>
    <% } %>
    <form method="post">
        <div>
            <label for="data">Data to hash</label>
            <input type="text" id="data" name="data"/>
        </div>
        <button type="submit">Hash</button>
    </form>
    <% if (md5Hash != null) { %>
    <p>
        MD5 Hash: <%= md5Hash %>
    </p>
    <% } %>
    <% if (sha1Hash != null) { %>
    <p>
        SHA1 Hash: <%= sha1Hash %>
    </p>
    <% } %>
</main>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>