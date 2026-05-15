package com.studentcourse.controller;

import java.io.IOException;
import java.util.List;
import com.studentcourse.dao.RegistrationDAO;
import com.studentcourse.model.Registration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registrations")
public class ViewRegistrationsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RegistrationDAO registrationDAO;

    @Override
    public void init() throws ServletException {
        registrationDAO = new RegistrationDAO();
        System.out.println("ViewRegistrationsServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("keyword");
            List<Registration> registrationList;

            if (keyword != null && !keyword.trim().isEmpty()) {
                registrationList = registrationDAO.searchRegistrations(keyword.trim());
            } else {
                registrationList = registrationDAO.getAllRegistrations();
            }

            request.setAttribute("registrationList", registrationList);
            request.setAttribute("keyword", keyword);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/registration-list.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load student enrollment registry dataset lists.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("ViewRegistrationsServlet destroyed");
    }
}
