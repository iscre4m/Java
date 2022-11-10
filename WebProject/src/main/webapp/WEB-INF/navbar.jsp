<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="step.learning.entities.User" %>
<%
//    String[] links = {};
    String contextPath = request.getContextPath();
    User user = (User) request.getAttribute("user");
    Boolean confirmationNeeded = (Boolean) request.getAttribute("confirmationNeeded");
%>
<nav>
    <ul>
        <li>
            <a href="<%= contextPath %>">
                HOME
            </a>
        </li>
<%--        <% for (String link : links) { %>--%>
<%--        <li>--%>
<%--            <a href="<%= link %>">--%>
<%--                <%= link.toUpperCase() %>--%>
<%--            </a>--%>
<%--        </li>--%>
<%--        <% } %>--%>
    </ul>
    <ul>
        <% if (user == null) { %>
        <li>
            <a href="<%= contextPath %>/register">REGISTER</a>
        </li>
        <li>
            <a href="<%= contextPath %>/login">LOGIN</a>
        </li>
        <% } else { %>
        <% if (confirmationNeeded) { %>
        <li>
            <a href="<%= contextPath %>/confirm"
               title="Email not confirmed">
                &#x1F4E7;
            </a>
        </li>
        <% } %>
        <li>
            <a href="<%= contextPath %>/profile">
                <img class="navbar-avatar"
                     src="<%= contextPath %>/image/<%= user.getAvatar() %>"
                     alt="<%= user.getUsername() %>"
                />
            </a>
        </li>
        <li>
            <form>
                <input type="hidden" name="logout" value="navbar-logout"/>
                <button type="submit">
                    LOGOUT
                </button>
            </form>
        </li>
        <% } %>
    </ul>
</nav>