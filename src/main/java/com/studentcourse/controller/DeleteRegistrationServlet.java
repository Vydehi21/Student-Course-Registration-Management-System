package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.RegistrationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete-registration")
public class DeleteRegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RegistrationDAO registrationDAO;

    @Override
    public void init() throws ServletException {
        registrationDAO = new RegistrationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/registrations");
                return;
            }

            int id = Integer.parseInt(idParam.trim());
            registrationDAO.deleteRegistration(id);

            response.sendRedirect(request.getContextPath() + "/registrations");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/registrations?error=invalidid");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred while deleting the registration.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
