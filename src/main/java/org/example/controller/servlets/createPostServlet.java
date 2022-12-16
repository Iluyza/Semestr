package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet (urlPatterns = "/createpost")
public class createPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/createpost.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (DatabaseManager.createPost(((User)req.getSession().getAttribute("User")).getId(),req.getParameter("title"),req.getParameter("text"))) {
            req.getSession().removeAttribute("PostCreateException");
            resp.sendRedirect(req.getContextPath() + "/userpage");
        }
        else {
            req.getSession().setAttribute("PostCreateException","Something goes wrong during post creation");
            resp.sendRedirect(req.getContextPath()+"/createpost");
        }
    }
}
