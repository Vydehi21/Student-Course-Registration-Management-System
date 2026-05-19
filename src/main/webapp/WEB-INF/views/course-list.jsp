<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.studentcourse.model.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Courses List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Course Catalog Inventory</h2>

    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/course/add">Add New Course</a>
    </div>
    <br>

    <% String error = request.getParameter("error"); %>
    <% if ("hasstudents".equals(error)) { %>
        <div class="error">Cannot delete course: Students are currently registered to it.</div>
    <% } else if ("deletefailed".equals(error)) { %>
        <div class="error">Failed to delete the course record. Please try again.</div>
    <% } else if ("exception".equals(error)) { %>
        <div class="error">A database connection error occurred.</div>
    <% } %>

    <div class="search-box">
        <form action="${pageContext.request.contextPath}/courses" method="get">
            <input type="text" name="keyword" placeholder="Search by course name or trainer..."
                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
            <button type="submit" class="search-btn">Search</button>
            <a class="reset-btn" href="${pageContext.request.contextPath}/courses">Reset</a>
        </form>
    </div>
    <br>

    <table>
        <tr>
            <th>Course ID</th>
            <th>Course Name</th>
            <th>Trainer Name</th>
            <th>Duration</th>
            <th>Tuition Fee</th>
            <th>Actions</th>
        </tr>
        <%
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
        %>
        <tr>
            <td><%= course.getCourseId() %></td>
            <td><%= course.getCourseName() %></td>
            <td><%= course.getTrainerName() %></td>
            <td><%= course.getDuration() %> months</td>
            <td>₹ <%= String.format("%.2f", course.getFees()) %></td>
            <td>
                <a class="action-btn edit-btn" href="${pageContext.request.contextPath}/course/edit?id=<%= course.getCourseId() %>">Edit</a>
                <a class="action-btn delete-btn" href="${pageContext.request.contextPath}/course/delete?id=<%= course.getCourseId() %>"
                   onclick="return confirm('Are you sure you want to delete this course?')">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6">No courses found matching your search.</td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
