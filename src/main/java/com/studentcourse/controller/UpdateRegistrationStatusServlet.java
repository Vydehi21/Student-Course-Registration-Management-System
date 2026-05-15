package com.studentcourse.controller;

import com.studentcourse.dao.RegistrationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-registration-status")
public class UpdateRegistrationStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            // Fixed: Use safe connection handling instead of leaking raw database connection references
            RegistrationDAO dao = new RegistrationDAO();
            dao.updateStatus(id, status);

            response.sendRedirect(request.getContextPath() + "/registrations");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update registration status parameters.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
