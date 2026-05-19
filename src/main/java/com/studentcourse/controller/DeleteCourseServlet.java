package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/course/delete")
public class DeleteCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/courses?error=invalidid");
                return;
            }

            int courseId = Integer.parseInt(idParam.trim());

            if (courseDAO.hasActiveRegistrations(courseId)) {
                response.sendRedirect(request.getContextPath() + "/courses?error=hasstudents");
                return;
            }

            boolean deleted = courseDAO.deleteCourse(courseId);
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/courses");
            } else {
                response.sendRedirect(request.getContextPath() + "/courses?error=deletefailed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?error=exception");
        }
    }
}
