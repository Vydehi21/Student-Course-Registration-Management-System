package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/delete")
public class DeleteStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/students?error=invalidid");
                return;
            }

            int studentId = Integer.parseInt(idParam.trim());

            if (studentDAO.hasRegistrations(studentId)) {
                response.sendRedirect(request.getContextPath() + "/students?error=registered");
                return;
            }

            boolean deleted = studentDAO.deleteStudent(studentId);
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/students");
            } else {
                response.sendRedirect(request.getContextPath() + "/students?error=deletefailed");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/students?error=invalidid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/students?error=exception");
        }
    }
    
    @Override
    public void destroy() {

        System.out.println(
                "DeleteStudentServlet destroyed");
    }
}
