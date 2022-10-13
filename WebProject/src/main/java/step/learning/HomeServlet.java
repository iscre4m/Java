package step.learning;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        String name = (String) session.getAttribute("name");
        String username = (String) session.getAttribute("username");

        req.setAttribute("name", name);
        req.setAttribute("username", username);

        if(name != null) {
            session.removeAttribute("name");
        }
        if(username != null) {
            session.removeAttribute("username");
        }

        req.getRequestDispatcher("index.jsp")
                .forward(req, resp);
    }
}