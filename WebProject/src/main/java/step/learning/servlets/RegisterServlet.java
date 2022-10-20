package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO;

    @Inject
    public RegisterServlet(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String regError = (String) session.getAttribute("regError");
        if (regError != null) {
            req.setAttribute("regError", regError);
            session.removeAttribute("regError");
        }

        req.setAttribute("pageBody", "register.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String name = req.getParameter("name");

        try {
            // region validation
            if (username == null || username.isEmpty()) {
                throw new Exception("Empty username not allowed");
            }
            if (username.contains(" ")) {
                throw new Exception("Spaces in username not allowed");
            }
            if (!userDAO.isUsernameUnique(username)) {
                throw new Exception("Username is already in use");
            }
            if (!password.equals(confirmPassword)) {
                throw new Exception("Passwords don't match");
            }
            // endregion
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            if (userDAO.add(user) == null) {
                throw new Exception("Server error, try again later");
            }
        } catch (Exception ex) {
            session.setAttribute("regError", ex.getMessage());
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        session.setAttribute("regOk", "Registration successful");
        resp.sendRedirect(req.getContextPath());
    }
}