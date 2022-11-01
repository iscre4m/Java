<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) request.getAttribute("user");
%>
<main class="confirm-email">
    <h1>Email confirmation</h1>
    <% if (user == null) { %>
    <p>Authorize to confirm email</p>
    <% } else { %>
    <form>
        <label>
            Confirmation code
            <input type="text" name="confirm"/>
        </label>
        <button type="submit">Submit</button>
    </form>
    <% } %>
</main>