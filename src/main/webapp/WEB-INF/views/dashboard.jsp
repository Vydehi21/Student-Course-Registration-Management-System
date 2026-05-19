<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Admin Dashboard</h2>

    <h3>Welcome, <span style="color: #007bff;">${loggedInUser != null ? loggedInUser : 'Admin'}</span></h3>
    <br>

    <div class="dashboard-cards">
        <div class="card">
            <h3>Total Students</h3>
            <p>${studentCount}</p>
        </div>

        <div class="card">
            <h3>Total Courses</h3>
            <p>${courseCount}</p>
        </div>

        <div class="card">
            <h3>Total Registrations</h3>
            <p>${registrationCount}</p>
        </div>
    </div>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/students">Manage Students</a>
        <a href="${pageContext.request.contextPath}/courses">Manage Courses</a>
        <a href="${pageContext.request.contextPath}/registrations">Manage Registrations</a>
        <a class="delete-btn" style="color: white; display: inline-block; padding: 10px 15px; border-radius: 5px;" 
           href="${pageContext.request.contextPath}/logout">Log Out</a>
    </div>
</div>
</body>
</html>
