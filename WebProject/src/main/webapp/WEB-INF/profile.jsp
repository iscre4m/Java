<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath = request.getContextPath();
    User user = (User) request.getAttribute("user");
    String avatar = user.getAvatar();
%>
<main>
    <% if (avatar != null) { %>
    <img class="profile-avatar"
         src="<%= contextPath %>/image/<%= avatar %>"
         alt="<%= user.getUsername() %>"/>
    <% } %>
    <fieldset>
        <legend>Editable</legend>
        <div class="field">
            <div>
                Name: <span data-field-name="name"><%= user.getName() %></span>
            </div>
            <div>
                <span class="cursor-pointer profile-edit">&#x270E;</span>
                <span class="cursor-pointer profile-confirm">&#x2705;</span>
                <span class="cursor-pointer profile-cancel">&#x274C;</span>
            </div>
        </div>
        <div class="field">
            <div>
                Username: <span data-field-name="username"><%= user.getUsername() %></span>
            </div>
            <div class="edit-icons">
                <span class="cursor-pointer profile-edit">&#x270E;</span>
                <span class="cursor-pointer profile-confirm">&#x2705;</span>
                <span class="cursor-pointer profile-cancel">&#x274C;</span>
            </div>
        </div>
        <div class="d-flex flex-column">
            <input id="avatarInput" type="file" hidden/>
            <span id="changeAvatar" class="m-auto cursor-pointer">Change avatar</span>
            <span id="avatarName" class="m-auto"></span>
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

        const changeAvatar = document.getElementById("changeAvatar");
        const avatarInput = document.getElementById("avatarInput");
        const avatarName = document.getElementById("avatarName");

        changeAvatar.addEventListener("click", () => avatarInput.click());
        avatarInput.addEventListener("input", () => {
            const filePath = avatarInput.value;
            const slashIndex = filePath.lastIndexOf("\\");
            avatarName.innerText = filePath.substring(slashIndex + 1);
        })
    })

    const editClick = (e) => {
        const fieldToEdit = e.target.closest('.field').querySelector("span");

        if (fieldToEdit) {
            fieldToEdit.setAttribute("contenteditable", "true");
            fieldToEdit.savedValue = fieldToEdit.innerText;
            fieldToEdit.focus();
        }
    }

    const confirmClick = (e) => {
        const fieldToEdit = e.target.closest('.field').querySelector("span");

        if (fieldToEdit) {
            fieldToEdit.removeAttribute("contenteditable");
            if (fieldToEdit.savedValue && fieldToEdit.savedValue !== fieldToEdit.innerText) {
                const fieldName = fieldToEdit.getAttribute("data-field-name");
                const url = "<%= request.getContextPath() %>/register?"
                    + fieldName + "=" + fieldToEdit.innerText;
                fetch(url, {
                    method: "PUT",
                    headers: {
                        "Connection": "close"
                    },
                    body: ""
                })
                    .then(r => r.text())
                    .then(r => {
                        if (r === "Success") {
                            location = location;
                        } else {
                            alert(r);
                            fieldToEdit.innerText = fieldToEdit.savedValue;
                        }
                    });
            }
        }
    }

    const cancelClick = (e) => {
        const fieldToEdit = e.target.closest('.field').querySelector("span");

        if (fieldToEdit) {
            if (fieldToEdit.savedValue) {
                fieldToEdit.innerText = fieldToEdit.savedValue;
            }
            fieldToEdit.removeAttribute("contenteditable");
        }
    }
</script>