package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConfirmationServlet extends HttpServlet {
    @Inject
    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String confirm = req.getParameter("confirm");

        if (confirm != null) {
            User user = userId == null
                    ? (User) req.getAttribute("user")
                    : userDAO.getUserById(userId);
            try {
                if (user == null) {
                    throw new Exception("Not authorized");
                }
                if (user.getEmailCode() == null) {
                    throw new Exception("Email already confirmed");
                }
                if (!user.getEmailCode().equals(confirm)) {
                    throw new Exception("Invalid confirmation code");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        req.setAttribute("pageBody", "confirm_email");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }
}