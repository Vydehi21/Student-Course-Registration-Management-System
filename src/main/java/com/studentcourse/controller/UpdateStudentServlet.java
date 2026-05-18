package com.studentcourse.controller;

import com.studentcourse.dao.StudentDAO;
import com.studentcourse.exception.InvalidStudentException;
import com.studentcourse.model.Student;
import com.studentcourse.validation.StudentValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/update")
public class UpdateStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO dao;

    @Override
    public void init() {
        // Fixed: Switched to default initialization to protect the database layer from connection pooling leak hazards
        dao = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Student s = new Student();
            s.setStudentId(Integer.parseInt(request.getParameter("id")));
            // Fixed: Re-aligned variable parameter retrieval key string to read 'studentName' matching student-edit.jsp input field parameters
            s.setStudentName(request.getParameter("studentName"));
            s.setEmail(request.getParameter("email"));
            s.setPhone(request.getParameter("phone"));

            String ageParam = request.getParameter("age");
            s.setAge(ageParam != null && !ageParam.trim().isEmpty() ? Integer.parseInt(ageParam.trim()) : 0);
            s.setCity(request.getParameter("city"));

            // Service domain validator rule evaluation execution
            StudentValidator.validate(s);

            boolean updated = dao.updateStudent(s);
            if (!updated) {
                throw new Exception("Student storage profile write operation returned failed state.");
            }

            response.sendRedirect(request.getContextPath() + "/students");

        } catch (SQLException e) {

            if (e.getMessage().contains("email")) {

                request.setAttribute(
                        "errorMessage",
                        "Email already exists");
                        
            } else if (e.getMessage().contains("phone")) {

                request.setAttribute(
                        "errorMessage",
                        "Phone number already exists");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Database error occurred");
            }

            request.getRequestDispatcher(
                    "/WEB-INF/views/student-form.jsp")
                    .forward(request, response);
        }
        catch (InvalidStudentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            // Re-populate and fallback variables cleanly for the presentation layer rendering framework loops
            request.setAttribute("student", extractFallbackFormState(request));
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal exception error stopped student data records update actions.");
            request.setAttribute("student", extractFallbackFormState(request));
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);
        }
    }

    // Isolated helper module to prevent duplicate parsing loops during exceptional view rendering rollbacks
    private Student extractFallbackFormState(HttpServletRequest request) {
        Student s = new Student();
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) s.setStudentId(Integer.parseInt(idStr));
            s.setStudentName(request.getParameter("studentName"));
            s.setEmail(request.getParameter("email"));
            s.setPhone(request.getParameter("phone"));
            String ageStr = request.getParameter("age");
            if (ageStr != null && !ageStr.trim().isEmpty()) s.setAge(Integer.parseInt(ageStr.trim()));
            s.setCity(request.getParameter("city"));
        } catch(Exception e) { /* Swallow fallback calculation edge-case crashes safely */ }
        return s;
    }
}
