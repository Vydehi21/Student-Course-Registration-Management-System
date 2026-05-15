<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin System Metrics Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Admin Management Dashboard</h2>

    <%-- Refactored: Utilized native expression language parsing instead of complex naked scriptlet castings --%>
    <h3>Welcome back, <span style="color: #007bff;">${loggedInUser != null ? loggedInUser : 'Administrator'}</span></h3>
    <br>

    <%-- Metric Inventory Summary Display Panels --%>
    <div class="dashboard-cards">
        <div class="card">
            <h3>Students Tracked</h3>
            <p>${studentCount}</p>
        </div>

        <div class="card">
            <h3>Courses Available</h3>
            <p>${courseCount}</p>
        </div>

        <div class="card">
            <h3>Enrollments Filled</h3>
            <p>${registrationCount}</p>
        </div>
    </div>

    <%-- Primary Core Functional Route Mappings --%>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/students">Manage Students Directory</a>
        <a href="${pageContext.request.contextPath}/courses">Manage Academic Catalog</a>
        <a href="${pageContext.request.contextPath}/registrations">Manage Student Enrollments</a>
        <a class="delete-btn" style="color: white; display: inline-block; padding: 10px 15px; border-radius: 5px;" 
           href="${pageContext.request.contextPath}/logout">Secure Log Out</a>
    </div>
</div>
</body>
</html>
