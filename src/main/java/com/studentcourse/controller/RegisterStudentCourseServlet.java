package com.studentcourse.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.dao.StudentDAO;
import com.studentcourse.dao.RegistrationDAO;
import com.studentcourse.exception.InvalidRegistrationException;
import com.studentcourse.model.Registration;
import com.studentcourse.validation.RegistrationValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register-student-course")
public class RegisterStudentCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private RegistrationDAO registrationDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        registrationDAO = new RegistrationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            String dateStr = request.getParameter("registrationDate");
            String status = request.getParameter("status");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateStr);
            Date sqlDate = new Date(utilDate.getTime());

            Registration r = new Registration();
            r.setStudentId(studentId);
            r.setCourseId(courseId);
            r.setRegistrationDate(sqlDate);
            r.setStatus(status);

            RegistrationValidator.validate(r);

            int result = registrationDAO.registerStudent(r);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/registrations");
            } else {
                handleFailure(request, response, "Registration failed on database nodes.");
            }

        } catch (InvalidRegistrationException e) {
            handleFailure(request, response, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // This captures our Trigger message: "Duplicate active registration is not allowed..."
            String msg = (e.getMessage() != null) ? e.getMessage() : "An internal error occurred processing registration.";
            handleFailure(request, response, msg);
        }
    }

    private void handleFailure(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {
        try {
            request.setAttribute("error", message);
            request.setAttribute("students", studentDAO.getAllStudents());
            request.setAttribute("courses", courseDAO.getAllCourses());
            request.getRequestDispatcher("/WEB-INF/views/registration-form.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
