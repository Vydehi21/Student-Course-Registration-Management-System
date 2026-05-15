package com.studentcourse.controller;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        // Enforces Section 12.2 console verification metrics
        System.out.println("LoginPageServlet initialized successfully via init()");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session Validation fallback check
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute("loggedInUser") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Forward to secure folder location matching Section 16 routing layout guidelines
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("LoginPageServlet destroyed via destroy()");
    }
}
