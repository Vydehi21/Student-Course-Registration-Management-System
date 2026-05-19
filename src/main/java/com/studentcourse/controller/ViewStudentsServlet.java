package com.studentcourse.controller;

import java.io.IOException;
import java.util.List;
import com.studentcourse.dao.StudentDAO;
import com.studentcourse.model.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class ViewStudentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        try {
            List<Student> students;

            if (keyword != null && !keyword.trim().isEmpty()) {
                students = studentDAO.searchStudents(keyword.trim());
            } else {
                students = studentDAO.getAllStudents();
            }

            request.setAttribute("students", students);
            request.setAttribute("keyword", keyword);
            
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/student-list.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("keyword", keyword);
            request.setAttribute("errorMessage", "An error occurred while loading the student records.");
            
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            rd.forward(request, response);
        }
    }
}
