package servlet;

import model.Post;
import model.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(new Post(Integer.valueOf(req.getParameter("id")), req.getParameter("name"), "decs", LocalDate.now()));
        res.sendRedirect(req.getContextPath() + "/posts.jsp");
    }
}
