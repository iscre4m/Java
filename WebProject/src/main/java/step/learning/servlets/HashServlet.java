package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import step.learning.services.hash.HashService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class HashServlet extends HttpServlet {
    @Inject
    @Named("MD5HashService")
    private HashService md5HashService;
    @Inject
    @Named("SHA1HashService")
    private HashService sha1HashService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Boolean emptyData = (Boolean) session.getAttribute("emptyData");
        String md5Hash = (String) session.getAttribute("md5Hash");
        String sha1Hash = (String) session.getAttribute("sha1Hash");

        req.setAttribute("emptyData", emptyData);
        req.setAttribute("md5Hash", md5Hash);
        req.setAttribute("sha1Hash", sha1Hash);

        if (emptyData != null) {
            session.removeAttribute("emptyData");
        }
        if (md5Hash != null) {
            session.removeAttribute("md5Hash");
        }
        if (sha1Hash != null) {
            session.removeAttribute("sha1Hash");
        }

        req.getRequestDispatcher("WEB-INF/hash.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = req.getParameter("data");
        HttpSession session = req.getSession();

        if (data.isBlank()) {
            session.setAttribute("emptyData", true);
        } else {
            session.setAttribute("md5Hash", md5HashService.hash(data));
            session.setAttribute("sha1Hash", sha1HashService.hash(data));
        }

        resp.sendRedirect("http://localhost:8080/WebProject/hash");
    }
}