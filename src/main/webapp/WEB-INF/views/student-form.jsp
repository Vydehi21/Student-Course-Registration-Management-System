<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Add New Student</h2>

    <% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/student/add" method="post" >
        <label>Student Name</label>
        <input type="text" name="studentName" >

        <label>Email Address</label>
        <input type="email" name="email" >

        <label>Phone Number (10 Digits)</label>
        <input type="text" name="phone" >

        <label>Age</label>
        <input type="number" name="age" >

        <label>City Location</label>
        <input type="text" name="city" >

        <button type="submit">Add Student</button>
    </form>
    <br>
    <a class="reset-btn" href="${pageContext.request.contextPath}/students">Back to Students List</a>
</div>
</body>
</html>
