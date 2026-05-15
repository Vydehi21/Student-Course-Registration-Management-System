package com.studentcourse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate active session contexts
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clean up persistent cookie metadata stores
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                // Fixed: Aligned key string selection target to match rememberedUsername identifier name
                if ("rememberedUsername".equals(c.getName())) {
                    c.setValue("");
                    c.setMaxAge(0); // Trigger immediate removal on client nodes
                    c.setPath("/");  // Match root path configuration
                    response.addCookie(c);
                }
            }
        }

        // Return user to public system landing page path
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
