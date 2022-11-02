<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
    String savedCode = (String) request.getAttribute("savedCode");
%>
<main>
    <h1>Email confirmation</h1>
    <% if (user == null) { %>
    <p>Authorize to confirm email</p>
    <% } else { %>
    <% if (error != null) { %>
    <h4>
        <%= error %>
    </h4>
    <% } %>
    <form>
        <div>
            <label for="confirm">Confirmation code</label>
            <input type="text"
                   id="confirm"
                   name="confirm"
                   value="<%= savedCode == null ? "" : savedCode %>"
            />
        </div>
        <button type="submit">Submit</button>
    </form>
    <% } %>
</main>