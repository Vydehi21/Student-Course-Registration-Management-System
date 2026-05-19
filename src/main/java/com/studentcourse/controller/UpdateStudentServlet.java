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
        dao = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Student s = new Student();
            s.setStudentId(Integer.parseInt(request.getParameter("id")));
            s.setStudentName(request.getParameter("studentName"));
            s.setEmail(request.getParameter("email"));
            s.setPhone(request.getParameter("phone"));

            String ageParam = request.getParameter("age");
            s.setAge(ageParam != null && !ageParam.trim().isEmpty() ? Integer.parseInt(ageParam.trim()) : 0);
            s.setCity(request.getParameter("city"));

            StudentValidator.validate(s);

            boolean updated = dao.updateStudent(s);
            if (!updated) {
                throw new Exception("Update operation failed.");
            }

            response.sendRedirect(request.getContextPath() + "/students");

        } catch (SQLException e) {
            String errorMsg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (errorMsg.contains("email")) {
                request.setAttribute("errorMessage", "Email already exists");
            } else if (errorMsg.contains("phone")) {
                request.setAttribute("errorMessage", "Phone number already exists");
            } else {
                request.setAttribute("errorMessage", "Database error occurred");
            }
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);
        } catch (InvalidStudentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("student", extractFallbackFormState(request));
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the student records.");
            request.setAttribute("student", extractFallbackFormState(request));
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);
        }
    }

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
        } catch(Exception e) {
            // ignore parsing exceptions here
        }
        return s;
    }
}
