<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Boolean emptyData = (Boolean) request.getAttribute("emptyData");
    String md5Hash = (String) request.getAttribute("md5Hash");
    String sha1Hash = (String) request.getAttribute("sha1Hash");
%>
<main>
    <% if (emptyData != null && emptyData) { %>
    <p>
        No data to hash
    </p>
    <% } %>
    <form>
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