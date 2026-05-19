package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.model.Course;
import com.studentcourse.exception.InvalidCourseException;
import com.studentcourse.validation.CourseValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/course/add")
public class AddCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/course-form.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String courseName = request.getParameter("courseName");
            String trainerName = request.getParameter("trainerName");
            String duration = request.getParameter("duration");

            String feesParam = request.getParameter("fees");
            double fees = (feesParam != null && !feesParam.trim().isEmpty()) ? Double.parseDouble(feesParam.trim()) : 0.0;

            Course course = new Course(courseName, trainerName, duration, fees);
            CourseValidator.validate(course);

            boolean inserted = courseDAO.addCourse(course);
            if (inserted) {
                response.sendRedirect(request.getContextPath() + "/courses");
            } else {
                request.setAttribute("errorMessage", "Failed to save course. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/course-form.jsp").forward(request, response);
            }

        } catch (InvalidCourseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/course-form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Course fees must be a valid number.");
            request.getRequestDispatcher("/WEB-INF/views/course-form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal system error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/course-form.jsp").forward(request, response);
        }
    }
}
