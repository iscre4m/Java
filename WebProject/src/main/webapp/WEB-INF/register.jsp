<jsp:include page="/WEB-INF/header.jsp"/>
<div>
    <form method="post">
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password">
        </div>
        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword">
        </div>
        <div>
            <label for="name">Name</label>
            <input type="text" id="name" name="name">
        </div>
        <button type="submit">Register</button>
    </form>
</div>
<jsp:include page="/WEB-INF/footer.jsp"/>