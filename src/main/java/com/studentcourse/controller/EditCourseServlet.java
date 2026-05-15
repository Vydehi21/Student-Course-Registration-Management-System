package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.model.Course;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/course/edit")
public class EditCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Safe: Manual session check code removed here as AuthFilter intercepts this route cleanly
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/courses");
                return;
            }

            int courseId = Integer.parseInt(idParam.trim());
            Course course = courseDAO.getCourseById(courseId);

            if (course == null) {
                response.sendRedirect(request.getContextPath() + "/courses");
                return;
            }

            request.setAttribute("course", course);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/course-edit.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }
}
