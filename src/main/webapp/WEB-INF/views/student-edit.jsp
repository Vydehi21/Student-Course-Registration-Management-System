<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.studentcourse.model.Student" %>
<%
Student student = (Student) request.getAttribute("student");
if (student == null) {
    response.sendRedirect(request.getContextPath() + "/students");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Edit Student Details</h2>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/student/update" method="post" >
        <input type="hidden" name="id" value="<%= student.getStudentId() %>">

        <label>Student Name</label>
        <input type="text" name="studentName" value="<%= student.getStudentName() %>" >

        <label>Email</label>
        <input type="email" name="email" value="<%= student.getEmail() %>">

        <label>Phone Number</label>
        <input type="text" name="phone" value="<%= student.getPhone() %>" >

        <label>Age</label>
        <input type="number" name="age" value="<%= student.getAge() %>" >

        <label>City</label>
        <input type="text" name="city" value="<%= student.getCity() %>" >

        <button type="submit">Update Student</button>
    </form>
    <br>
    <a class="reset-btn" href="${pageContext.request.contextPath}/students">Back to Students List</a>
</div>
</body>
</html>
