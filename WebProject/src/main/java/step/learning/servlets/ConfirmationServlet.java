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

                if (userDAO.confirmEmail(user)) {
                    resp.sendRedirect(req.getContextPath());
                    return;
                }

                throw new Exception("Server error, try again later");
            } catch (Exception ex) {
                req.setAttribute("error", ex.getMessage());
                req.setAttribute("savedCode", confirm);
            }
        }

        req.setAttribute("pageBody", "confirm_email.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }
}