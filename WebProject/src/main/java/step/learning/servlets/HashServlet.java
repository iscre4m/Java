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
        String data = req.getParameter("data");

        if (data != null) {
            if (data.equals("")) {
                req.setAttribute("emptyData", true);
            } else {
                req.setAttribute("md5Hash", md5HashService.hash(data));
                req.setAttribute("sha1Hash", sha1HashService.hash(data));
            }
        }

        req.getRequestDispatcher("WEB-INF/hash.jsp")
                .forward(req, resp);
    }
}