package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (
                DatabaseManager
                        .getUser(
                                req.getParameter("userName"),
                                req.getParameter("userPassword"),
                                req)) {
            req.getSession().removeAttribute("LoginException");
            resp.sendRedirect(req.getContextPath() + "/userpage");
        } else {
            req.getSession().setAttribute("LoginException","Something goes wrong during authentication");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
