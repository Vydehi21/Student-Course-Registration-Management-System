<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.studentcourse.model.Student, com.studentcourse.model.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Course Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Register Student To Course</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/registrations">View Registrations</a>
    </div>
    <br><br>

    <form action="${pageContext.request.contextPath}/register-student-course" method="post" novalidate>
        <label>Select Student</label>
        <select name="studentId" required>
            <option value="">-- Choose a Student --</option>
            <%
            List<Student> students = (List<Student>) request.getAttribute("students");
            if (students != null) {
                for (Student s : students) {
            %>
            <option value="<%= s.getStudentId() %>"><%= s.getStudentName() %></option>
            <%
                }
            }
            %>
        </select>

        <label>Select Course</label>
        <select name="courseId" required>
            <option value="">-- Choose a Course --</option>
            <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            if (courses != null) {
                for (Course c : courses) {
            %>
            <option value="<%= c.getCourseId() %>"><%= c.getCourseName() %></option>
            <%
                }
            }
            %>
        </select>

        <label>Registration Date</label>
        <input type="date" name="registrationDate" required>

        <label>Initial Processing Status</label>
        <select name="status">
            <option value="ACTIVE">ACTIVE</option>
            <option value="COMPLETED">COMPLETED</option>
            <option value="CANCELLED">CANCELLED</option>
        </select>

        <button type="submit" class="primary-btn">Complete Course Registration</button>
    </form>
</div>
</body>
</html>
