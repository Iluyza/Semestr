package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (DatabaseManager.createUser(
                req.getParameter("userName"),
                req.getParameter("userPassword"),
                req.getParameter("userEmail"))
                &&
                DatabaseManager.getUser(
                        req.getParameter("userName"),
                        req.getParameter("userPassword"),
                        req)) {
            req.getSession().removeAttribute("RegisterException");
            resp.sendRedirect(req.getContextPath() + "/userpage");
        } else {
            req.getSession().setAttribute("RegisterException","Something goes wrong during registration");
            resp.sendRedirect(req.getContextPath() + "/register");
        }
    }
}
