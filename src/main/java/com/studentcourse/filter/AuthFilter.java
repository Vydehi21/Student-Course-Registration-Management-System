package com.studentcourse.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Use getServletPath() instead of manually substring-ing the full URI string
        String path = request.getServletPath();

        // Clear public routing configuration parameters matching SRS Section 15.1
        boolean isPublic = path.equals("/login") 
                        || path.equals("/login-action") 
                        || path.equals("/index.jsp")
                        || path.startsWith("/css/"); // Allow static styling resources through

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (loggedIn || isPublic) {
            // User is either authorized or accessing an open resource path
            chain.doFilter(request, response);
        } else {
            // Unauthorized entry: execution halts and shifts to the login gateway
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}