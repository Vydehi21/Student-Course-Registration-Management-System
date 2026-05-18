package com.studentcourse.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.studentcourse.dao.StudentDAO;
import com.studentcourse.exception.InvalidStudentException;
import com.studentcourse.model.Student;
import com.studentcourse.validation.StudentValidator;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/student/add")
public class AddStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO;

    // Lifecycle init
    @Override
    public void init() throws ServletException {

        studentDAO = new StudentDAO();

        System.out.println(
                "AddStudentServlet initialized");
    }

    // Show form
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

       

        RequestDispatcher rd =
                request.getRequestDispatcher(
                        "/WEB-INF/views/student-form.jsp");

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Student s = new Student();

            s.setStudentName(
                    request.getParameter("studentName"));

            s.setEmail(
                    request.getParameter("email"));

            s.setPhone(
                    request.getParameter("phone"));

            String ageParam = request.getParameter("age");

            if(ageParam != null && !ageParam.trim().isEmpty()) {
                s.setAge(Integer.parseInt(ageParam));
            } else {
                s.setAge(0);
            }
            s.setCity(
                    request.getParameter("city"));

            // VALIDATION
            StudentValidator.validate(s);

            studentDAO.addStudent(s);

            response.sendRedirect(
                    request.getContextPath()
                    + "/students");

        } catch (InvalidStudentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage());

            RequestDispatcher rd =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/student-form.jsp");
            rd.forward(request, response);

            } catch (SQLException e) {

                if (e.getMessage().contains("email")) {

                    request.setAttribute(
                            "errorMessage",
                            "Email already exists");
                            
                } else if (e.getMessage().contains("phone")) {

                    request.setAttribute(
                            "errorMessage",
                            "Phone number already exists");

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Database error occurred");
                }

                request.getRequestDispatcher(
                        "/WEB-INF/views/student-form.jsp")
                        .forward(request, response);
            }
        
        catch (Exception e) {

                e.printStackTrace();

                request.setAttribute(
                        "errorMessage",
                        "Something went wrong");

                RequestDispatcher rd =
                        request.getRequestDispatcher(
                                "/WEB-INF/views/student-form.jsp");

                rd.forward(request, response);
            }
        }


    // Lifecycle destroy
    @Override
    public void destroy() {

        System.out.println(
                "AddStudentServlet destroyed");
    }
}