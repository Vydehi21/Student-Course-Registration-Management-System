<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.studentcourse.model.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Active Training Courses Inventory</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Active Training Courses Inventory</h2>

    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/course/add">Add New Course</a>
    </div>
    <br>

    <%-- Fixed: Safe message tracking placement inside the structural rendering framework --%>
    <% String error = request.getParameter("error"); %>
    <% if ("hasstudents".equals(error)) { %>
        <div class="error">Deletion Denied: Active registrations are currently tied to this course.</div>
    <% } else if ("deletefailed".equals(error)) { %>
        <div class="error">Deletion Failed: Record validation checks failed on storage nodes.</div>
    <% } else if ("exception".equals(error)) { %>
        <div class="error">System Exception occurred processing database commands.</div>
    <% } %>

    <div class="search-box">
        <form action="${pageContext.request.contextPath}/courses" method="get">
            <input type="text" name="keyword" placeholder="Search by course name or trainer..."
                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
            <button type="submit" class="search-btn">Search Catalog</button>
            <a class="reset-btn" href="${pageContext.request.contextPath}/courses">Reset View</a>
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
            <th>Actions Available</th>
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
            <td><%= course.getDuration() %></td>
            <td>₹ <%= String.format("%.2f", course.getFees()) %></td>
            <td>
                <a class="action-btn edit-btn" href="${pageContext.request.contextPath}/course/edit?id=<%= course.getCourseId() %>">Edit</a>
                <a class="action-btn delete-btn" href="${pageContext.request.contextPath}/course/delete?id=<%= course.getCourseId() %>"
                   onclick="return confirm('Are you sure you want to permanently delete this course from the system configuration?')">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <%-- Fixed: Aligned colspan metric from 5 to 6 elements to secure grid symmetry --%>
            <td colspan="6">No catalog data matching search criteria found inside the records system.</td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
