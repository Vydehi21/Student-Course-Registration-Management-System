<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%
String rememberedUsername = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie cookie : cookies) {
        if ("rememberedUsername".equals(cookie.getName())) {
            rememberedUsername = cookie.getValue();
        }
    }
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container" style="max-width: 450px; margin-top: 100px;">
    <h2>Admin Login</h2>

    <% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login-action" method="post" novalidate>
        <label>Username</label>
        <input type="text" name="username" value="<%= rememberedUsername %>" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <div class="remember" style="margin: 15px 0;">
            <input type="checkbox" name="remember" id="remember" <%= !rememberedUsername.isEmpty() ? "checked" : "" %>>
            <label style="display: inline; font-weight: normal; margin: 0 0 0 5px;" for="remember">Remember Username</label>
        </div>

        <button type="submit" class="primary-btn" style="width: 100%;">Login</button>
    </form>
</div>
</body>
</html>
