<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.studentcourse.model.Course" %>
<%
Course course = (Course) request.getAttribute("course");
if (course == null) {
    response.sendRedirect(request.getContextPath() + "/courses");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Course</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Edit Course Details</h2>
    
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/course/update" method="post" novalidate>
        <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">

        <label>Course Name</label>
        <input type="text" name="courseName" value="<%= course.getCourseName() %>" required>

        <label>Trainer Name</label>
        <input type="text" name="trainerName" value="<%= course.getTrainerName() %>" required>

        <label>Duration</label>
        <input type="text" name="duration" value="<%= course.getDuration() %>" required>

        <label>Tuition Fee (INR)</label>
        <input type="number" step="0.01" name="fees" value="<%= course.getFees() %>" required>

        <button type="submit">Update Course</button>
    </form>
    <br>
    <a class="reset-btn" href="${pageContext.request.contextPath}/courses">Back to Course List</a>
</div>
</body>
</html>
