package com.studentcourse.controller;

import java.io.IOException;
import java.util.List;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.model.Course;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/courses")
public class ViewCoursesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        try {
            List<Course> courses;

            if (keyword != null && !keyword.trim().isEmpty()) {
                courses = courseDAO.searchCourses(keyword.trim());
            } else {
                courses = courseDAO.getAllCourses();
            }

            request.setAttribute("courses", courses);
            request.setAttribute("keyword", keyword);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/course-list.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("keyword", keyword);
            request.setAttribute("errorMessage", "An error occurred while loading the courses.");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            rd.forward(request, response);
        }
    }
}
