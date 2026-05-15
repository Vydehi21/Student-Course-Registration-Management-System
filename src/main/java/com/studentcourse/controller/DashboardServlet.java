package com.studentcourse.controller;

import com.studentcourse.dao.StudentDAO;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private RegistrationDAO registrationDAO;

    @Override
    public void init() {
        // Fixed: Instantiated safely using no-argument constructors to eliminate static tracking leaks
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        registrationDAO = new RegistrationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Fetch system ledger calculations safely via on-demand request windows
            int studentCount = studentDAO.countStudents();
            int courseCount = courseDAO.countCourses();
            int registrationCount = registrationDAO.countRegistrations();

            request.setAttribute("studentCount", studentCount);
            request.setAttribute("courseCount", courseCount);
            request.setAttribute("registrationCount", registrationCount);

            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal system error occurred loading metrics metrics.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
