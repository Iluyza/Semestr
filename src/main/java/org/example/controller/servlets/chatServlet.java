package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet(urlPatterns = "/chat")
public class chatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("User");
        req.getSession().setAttribute("allMessages", DatabaseManager.getMessages(user.getUsername(), req.getParameter("name")));
        req.getSession().setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("User");
        String name = req.getParameter("name");
        String message = req.getParameter("message");
        DatabaseManager.createMessage(user.getUsername(), name, message);
        resp.sendRedirect(req.getContextPath() + "/chat?name=" + name);
    }
}
