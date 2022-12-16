package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Post;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet(urlPatterns = "/changepost")
public class changePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/changepost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatabaseManager.postUpdate(Post.builder()
                .id((int) req.getAttribute("postID"))
                .name(req.getParameter("postName"))
                .text(req.getParameter("postText"))
                .build());
        resp.sendRedirect("/feed");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = (int) req.getAttribute("postID");
        DatabaseManager.deletePost(id);
        resp.sendRedirect("/feed");
    }
}
