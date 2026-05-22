<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Course</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Add New Course</h2>

    <% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/course/add" method="post" novalidate>
        <label>Course Name</label>
        <input type="text" name="courseName" required>

        <label>Trainer Name</label>
        <input type="text" name="trainerName" required>

        <label>Duration Description (in months)</label>
        
        <input type="number" name="duration" min="1" step="1" required >

        <label>Tuition Fee (INR)</label>
        <input type="number" step="0.01" name="fees" required>

        <button type="submit">Add Course</button>
    </form>
    <br>
    <a class="reset-btn" href="${pageContext.request.contextPath}/courses">Back to Course List</a>
</div>
</body>
</html>
