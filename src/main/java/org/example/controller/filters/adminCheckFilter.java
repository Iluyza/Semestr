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

@WebFilter(urlPatterns = "/list")
public class adminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession() != null) {
            if (req.getSession().getAttribute("User") != null) {
                if (PermissionManager.checkPermission((User) req.getSession().getAttribute("User"))) {
                    req.getSession().setAttribute("permission", "yeah");
                } else {
                    req.getSession().removeAttribute("permission");
                }
            }
        }
        chain.doFilter(req, res);
    }
}
