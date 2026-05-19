<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.studentcourse.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Students</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Student Management</h2>

    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/student/add">Add New Student</a>
    </div>
    <br>

    <% String error = request.getParameter("error"); %>
    <% if ("registered".equals(error)) { %>
        <div class="error">Cannot delete student: This student is currently enrolled in a course.</div>
    <% } else if ("deletefailed".equals(error)) { %>
        <div class="error">Failed to delete student. Please try again.</div>
    <% } else if ("invalidid".equals(error)) { %>
        <div class="error">Invalid student ID.</div>
    <% } else if ("exception".equals(error)) { %>
        <div class="error">A database error occurred while processing your request.</div>
    <% } %>

    <div class="search-box">
        <form method="get" action="${pageContext.request.contextPath}/students">
            <input type="text" name="keyword" placeholder="Search by name, email or city..."
                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
            <button type="submit" class="search-btn">Search</button>
            <a class="reset-btn" href="${pageContext.request.contextPath}/students">Reset</a>
        </form>
    </div>
    <br>

    <table border="1" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Age</th>
            <th>City</th>
            <th>Actions</th>
        </tr>
        <%
        List<Student> students = (List<Student>) request.getAttribute("students");
        if (students != null && !students.isEmpty()) {
            for (Student s : students) {
        %>
        <tr>
            <td><%= s.getStudentId() %></td>
            <td><%= s.getStudentName() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getPhone() %></td>
            <td><%= s.getAge() %></td>
            <td><%= s.getCity() %></td>
            <td>
                <a class="action-btn edit-btn" href="${pageContext.request.contextPath}/student/edit?id=<%= s.getStudentId() %>">Edit</a>
                
                <form action="${pageContext.request.contextPath}/student/delete" method="post" class="inline-form">
                    <input type="hidden" name="id" value="<%= s.getStudentId() %>">
                    <button type="submit" class="action-btn delete-btn" style="border: none; cursor: pointer;"
                            onclick="return confirm('Are you sure you want to delete this student?')">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="7">No students found.</td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
