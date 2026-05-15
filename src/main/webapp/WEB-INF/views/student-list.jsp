<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.studentcourse.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Students Records Ledger</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Students Management Registry</h2>

    <%-- Navigation Control Triggers --%>
    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/student/add">Add New Student</a>
    </div>
    <br>

    <%-- Dynamic Validation Error Messages --%>
    <% String error = request.getParameter("error"); %>
    <% if ("registered".equals(error)) { %>
        <div class="error">Deletion Denied: This student is actively linked to course registration profiles.</div>
    <% } else if ("deletefailed".equals(error)) { %>
        <div class="error">Deletion Failed: Record not found or database constraints violated.</div>
    <% } else if ("invalidid".equals(error)) { %>
        <div class="error">Operation Rejected: The provided student identification marker is missing or invalid.</div>
    <% } else if ("exception".equals(error)) { %>
        <div class="error">System Exception occurred processing structural file actions.</div>
    <% } %>

    <%-- Search Query Panel Filtering Tool --%>
    <div class="search-box">
        <form method="get" action="${pageContext.request.contextPath}/students">
            <input type="text" name="keyword" placeholder="Search by name, email or city..."
                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
            <button type="submit" class="search-btn">Search</button>
            <a class="reset-btn" href="${pageContext.request.contextPath}/students">Reset Ledger</a>
        </form>
    </div>
    <br>

    <%-- Master Ledger Data Table --%>
    <table border="1" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Age</th>
            <th>City</th>
            <th>Actions Available</th>
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
                <%-- Edit Action Route Link --%>
                <a class="action-btn edit-btn" href="${pageContext.request.contextPath}/student/edit?id=<%= s.getStudentId() %>">Edit</a>
                
                <%-- Fixed: Refactored out of unsecured GET links into a robust POST submission block --%>
                <form action="${pageContext.request.contextPath}/student/delete" method="post" class="inline-form">
                    <input type="hidden" name="id" value="<%= s.getStudentId() %>">
                    <button type="submit" class="action-btn delete-btn" style="border: none; cursor: pointer;"
                            onclick="return confirm('Are you sure you want to permanently delete this student record?')">
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
            <%-- Fixed: Realigned table grid layout span matching exactly 7 headers --%>
            <td colspan="7">No matching student entities discovered in system storage.</td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
