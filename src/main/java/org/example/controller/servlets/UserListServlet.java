package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.model.managers.DatabaseManager;
import org.example.model.managers.PermissionManager;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet(urlPatterns = "/list")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LinkedList<User> users = new LinkedList<>();
        if (req.getParameter("search") == null) {
            DatabaseManager.getUsers(users);
        } else {
            DatabaseManager.getUsers(users, req.getParameter("search"));
        }
        req.getSession(true).setAttribute("userlist", users);
        req.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager.deleteUser(Integer.parseInt(req.getParameter("id")));
        String url = req.getContextPath() + "/list";
        if (req.getParameter("search") != null) {
            url = url + "?search=" + req.getParameter("search");
        }
        resp.sendRedirect(url);
    }
}
