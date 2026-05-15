<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.studentcourse.model.Registration" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Enrollment Ledger</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h2>Student Course Registrations Ledger</h2>

    <div class="top-links">
        <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Back To Dashboard</a>
        <a class="add-btn" href="${pageContext.request.contextPath}/registration/add">Add New Registration</a>
    </div>
    <br>

    <div class="search-box">
        <form action="${pageContext.request.contextPath}/registrations" method="get">
            <input type="text" name="keyword" placeholder="Search by student, course, or status..."
                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
            <button type="submit" class="search-btn">Search Registry</button>
            <a class="reset-btn" href="${pageContext.request.contextPath}/registrations">Reset View</a>
        </form>
    </div>
    <br>

    <table>
        <tr>
            <th>ID</th>
            <th>Student Name</th>
            <th>Course Enrolled</th>
            <th>Registration Date</th>
            <th>Processing Status</th>
            <th>Actions Management</th>
        </tr>
        <%
        List<Registration> registrationList = (List<Registration>) request.getAttribute("registrationList");
        if (registrationList != null && !registrationList.isEmpty()) {
            for (Registration r : registrationList) {
        %>
        <tr>
            <td><%= r.getRegistrationId() %></td>
            <td><%= r.getStudentName() %></td>
            <td><%= r.getCourseName() %></td>
            <td><%= r.getRegistrationDate() %></td>
            <td>
                <span class="status-label status-<%= r.getStatus().toLowerCase() %>">
                    <%= r.getStatus() %>
                </span>
            </td>
            <td>
                <!-- inline form updates status parameters -->
                <form action="${pageContext.request.contextPath}/update-registration-status" method="post" class="inline-form">
                    <input type="hidden" name="id" value="<%= r.getRegistrationId() %>">
                    <select name="status" style="width: auto; margin-bottom: 0; padding: 5px;">
                        <option value="ACTIVE" <%= "ACTIVE".equals(r.getStatus()) ? "selected" : "" %>>ACTIVE</option>
                        <option value="COMPLETED" <%= "COMPLETED".equals(r.getStatus()) ? "selected" : "" %>>COMPLETED</option>
                        <option value="CANCELLED" <%= "CANCELLED".equals(r.getStatus()) ? "selected" : "" %>>CANCELLED</option>
                    </select>
                    <button type="submit" class="edit-btn" style="padding: 5px 10px; font-size: 13px;">Update</button>
                </form>

                <!-- Fixed: Action path rerouted to hit the newly introduced delete handler servlet -->
                <form action="${pageContext.request.contextPath}/delete-registration" method="post" class="inline-form">
                    <input type="hidden" name="id" value="<%= r.getRegistrationId() %>">
                    <button type="submit" class="delete-btn" style="padding: 5px 10px; font-size: 13px;"
                            onclick="return confirm('Are you sure you want to permanently delete this course registration profile?')">
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
            <td colspan="6">No student course registration profiles discovered in system storage.</td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
