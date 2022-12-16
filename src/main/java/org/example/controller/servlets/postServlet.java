package org.example.controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Post;
import org.example.model.managers.DatabaseManager;

import java.io.IOException;

@WebServlet(urlPatterns = "/post")
public class postServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("post", DatabaseManager.getPost(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/view/post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("update".equals(req.getParameter("action"))) {
            DatabaseManager.postUpdate(Post.builder()
                    .id(Integer.parseInt(req.getParameter("id")))
                    .name(req.getParameter("postName"))
                    .text(req.getParameter("postText"))
                    .build());
            resp.sendRedirect(req.getContextPath() + (req.getSession().getAttribute("toReturn")));
        } else if ("delete".equals(req.getParameter("action"))) {
            DatabaseManager.deletePost(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + (req.getSession().getAttribute("toReturn")));
        }
    }
}
