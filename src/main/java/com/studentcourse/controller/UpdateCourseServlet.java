package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.CourseDAO;
import com.studentcourse.exception.InvalidCourseException;
import com.studentcourse.model.Course;
import com.studentcourse.validation.CourseValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/course/update")
public class UpdateCourseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Course course = new Course();

        try {
            course.setCourseId(Integer.parseInt(request.getParameter("courseId")));
            course.setCourseName(request.getParameter("courseName"));
            course.setTrainerName(request.getParameter("trainerName"));
            // Fixed: Switched from Integer parsing to safe String assignment matching SRS specification rules
            course.setDuration(request.getParameter("duration"));
            
            String feesParam = request.getParameter("fees");
            course.setFees(feesParam != null && !feesParam.trim().isEmpty() ? Double.parseDouble(feesParam.trim()) : 0.0);

            // Execute Business logic domain validation
            CourseValidator.validate(course);

            boolean updated = courseDAO.updateCourse(course);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/courses");
            } else {
                request.setAttribute("errorMessage", "Course specification update failed on storage systems.");
                request.setAttribute("course", course);
                request.getRequestDispatcher("/WEB-INF/views/course-edit.jsp").forward(request, response);
            }

        } catch (InvalidCourseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("course", course);
            request.getRequestDispatcher("/WEB-INF/views/course-edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Format parsing restriction error: Tuition fees must be a valid numeric calculation amount.");
            request.setAttribute("course", course);
            request.getRequestDispatcher("/WEB-INF/views/course-edit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to execute system catalog course updates.");
            request.setAttribute("course", course);
            request.getRequestDispatcher("/WEB-INF/views/course-edit.jsp").forward(request, response);
        }
    }
}
