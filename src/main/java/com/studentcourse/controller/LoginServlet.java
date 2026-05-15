package com.studentcourse.controller;

import java.io.IOException;
import com.studentcourse.dao.AdminDAO;
import com.studentcourse.exception.InvalidLoginException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login-action")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
        // Enforces Section 12.2 console verification metrics
        System.out.println("LoginServlet initialized successfully via init()");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect raw GET requests to the standalone rendering servlet
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        username = (username != null) ? username.trim() : "";
        password = (password != null) ? password.trim() : "";

        try {
            // Section 19.1 Server-side login parameter null/empty checks
            if (username.isEmpty()) {
                throw new InvalidLoginException("Username is required");
            }
            if (password.isEmpty()) {
                throw new InvalidLoginException("Password is required");
            }

            // Database validation call handling
            boolean isValid = adminDAO.validateAdmin(username, password);
            if (!isValid) {
                throw new InvalidLoginException("Invalid username or password");
            }

            // Establish fresh HTTP Session bounds matching Section 9.1.4 / 10.1 rules
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            session.setAttribute("loginTime", new java.util.Date());
            session.setMaxInactiveInterval(30 * 60); // 30 Minute timeout session token parameters

            // Core Cookie Rule Enforcement Block (Section 9.1.6 / 11.2)
            Cookie cookie = new Cookie("rememberedUsername", username);
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // Standardized baseline global scoping paths

            if (remember != null) {
                cookie.setMaxAge(7 * 24 * 60 * 60); // Retain cookie property values for 7 Days
            } else {
                cookie.setMaxAge(0); // Instantly erase active persistent client tracking cache cookies
            }
            response.addCookie(cookie);

            // Successful Login Rule (Page 4, Section 7): Use sendRedirect
            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (InvalidLoginException e) {
            request.setAttribute("errorMessage", e.getMessage());
            // Failed Login Rule (Page 4, Section 7): Use RequestDispatcher
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal system authentication error occurred.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("LoginServlet destroyed via destroy()");
    }
}
