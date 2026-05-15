package com.studentcourse.controller;

import com.studentcourse.dao.StudentDAO;
import com.studentcourse.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/student/edit")
public class EditStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO dao;

    @Override
    public void init() {
        // Fixed: Instantiate without assigning sticky lifecycle connections to prevent timeouts
        dao = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/students");
                return;
            }

            int id = Integer.parseInt(idParam.trim());
            Student student = dao.getStudentById(id);

            if (student == null) {
                response.sendRedirect(request.getContextPath() + "/students");
                return;
            }

            request.setAttribute("student", student);
            request.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/students");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/students");
        }
    }
}