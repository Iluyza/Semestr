package org.example.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebFilter(urlPatterns = {"/feed", "/createpost", "/chat", "/post", "/userpage"})
public class registerFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!(req.getSession() != null && req.getSession().getAttribute("User") != null)) {
            res.sendRedirect(req.getContextPath() + "/main");
            return;
        }
        chain.doFilter(req, res);
    }
}
