<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Application Error Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container" style="max-width: 550px; text-align: center; margin-top: 100px; border-top: 4px solid #dc3545;">
    <h2 style="color: #dc3545;">System Runtime Interruption</h2>
    <br>
    <p style="color: #6c757d; font-size: 16px;">
        ${errorMessage != null ? errorMessage : 'An unexpected exception layer process malfunction stopped execution.'}
    </p>
    <br><br>
    <a class="reset-btn" href="${pageContext.request.contextPath}/dashboard">Return to Secure Dashboard Workspace</a>
</div>
</body>
</html>
