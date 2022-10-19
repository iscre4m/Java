<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String host = request.getContextPath();
%>
<html>
<head>
  <title>Web Project</title>
  <link rel="stylesheet" href="<%= host %>/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp"/>