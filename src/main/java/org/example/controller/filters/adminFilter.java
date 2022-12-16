package org.example.controller.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.model.managers.PermissionManager;

import java.io.IOException;

@WebFilter(urlPatterns = "/admin")
public class adminFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!(req.getSession() != null
                &&
                req.getSession().getAttribute("User") != null
                &&
                PermissionManager.checkPermission((User) (req.getSession().getAttribute("User"))))) {
            res.sendRedirect(req.getContextPath()+"/main");
            return;
        }
        chain.doFilter(req, res);
    }
}
