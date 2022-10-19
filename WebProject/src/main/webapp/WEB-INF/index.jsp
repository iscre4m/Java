<jsp:include page="/WEB-INF/header.jsp"/>
<%
    String name = (String) request.getAttribute("name");
    String username = (String) request.getAttribute("username");
    String authError = (String) request.getAttribute("authError");
    String userId = (String) request.getAttribute("userId");
%>
<main>
    <% if (authError == null) { %>
    <h1>Welcome</h1>
    <% if (name != null && !name.equals("")) {%>
    <p>
        Name: <%= name %>
    </p>
    <% } %>
    <% if (username != null && !username.equals("")) { %>
    <p>
        Username: <%= username %>
    </p>
    <% } %>
    <% if (userId != null && !userId.equals("")) { %>
    <p>
        User ID: <%= userId %>
    </p>
    <% } %>
    <% } else { %>
    <h1>
        <%= authError %>
    </h1>
    <% } %>
</main>
<jsp:include page="/WEB-INF/footer.jsp"/>