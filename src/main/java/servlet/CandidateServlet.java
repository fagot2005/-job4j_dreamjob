package servlet;

import model.Candidate;
import model.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(new Candidate(Integer.valueOf(req.getParameter("id")), req.getParameter("name")));
        res.sendRedirect(req.getContextPath() + "/candidates.jsp");
    }
}
