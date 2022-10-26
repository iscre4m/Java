<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath = (String) request.getContextPath();
    User user = (User) request.getAttribute("user");
%>
<main>
    <h1>User Profile</h1>
    <img class="profile-avatar"
         src="<%= contextPath %>/image/<%= user.getAvatar() %>"
         alt="<%= user.getUsername() %>"/>
    <fieldset>
        <legend>Editable</legend>
        <div>
            <span>Name: <b><%= user.getName() %></b></span>
            <span class="profile-edit">&#x270E;</span>
            <span class="profile-confirm">&#x2705;</span>
            <span class="profile-cancel">&#x274C;</span>
        </div>
    </fieldset>
</main>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        for (let element of document.getElementsByClassName("profile-edit")) {
            element.addEventListener("click", editClick);
        }
        for (let element of document.getElementsByClassName("profile-confirm")) {
            element.addEventListener("click", confirmClick);
        }
        for (let element of document.getElementsByClassName("profile-cancel")) {
            element.addEventListener("click", cancelClick);
        }
    })

    const editClick = (e) => {
        const b = e.target.closest('div').querySelector("b");
        if (b) {
            b.setAttribute("contenteditable", "true");
            b.savedValue = b.innerText;
            b.focus();
        }
    }

    const confirmClick = (e) => {
        const b = e.target.closest('div').querySelector("b");
        if (b) {
            b.removeAttribute("contenteditable");
            if (b.savedValue && b.savedValue !== b.innerText) {
                fetch("<%= request.getContextPath() %>/register"
                    + "?name=" + b.innerText, {
                    method: "PUT",
                    headers: {
                        "Connection": "close"
                    },
                    body: ""
                })
                    .then(r => r.text())
                    .then(r => console.log(r));
            }
        }
    }

    const cancelClick = (e) => {
        const b = e.target.closest('div').querySelector("b");
        if (b) {
            if (b.savedValue) {
                b.innerText = b.savedValue;
            }
            b.removeAttribute("contenteditable");
        }
    }
</script>