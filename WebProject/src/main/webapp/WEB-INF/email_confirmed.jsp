<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Boolean confirmed = (Boolean) request.getAttribute("confirmed");
%>
<main>
    <% if (confirmed != null && confirmed) { %>
    <h1>Email confirmed &#x1F4E7;</h1>
    <% } %>
</main>