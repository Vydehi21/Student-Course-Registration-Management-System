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
        // Safe: Initialized using the default constructor to rely on on-demand connection processing bounds
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
            
            // Execute safe transaction removal out of storage systems
            registrationDAO.deleteRegistration(id);

            response.sendRedirect(request.getContextPath() + "/registrations");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/registrations?error=invalidid");
        } catch (Exception e) {
            e.printStackTrace();
            
            // Fixed: Encapsulated error handling fallback pointing to the secure views layer structure
            request.setAttribute("errorMessage", "An unexpected failure occurred while trying to erase the record registration.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
