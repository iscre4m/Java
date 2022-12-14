package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.validator.routines.EmailValidator;
import step.learning.dao.UserDAO;
import step.learning.entities.User;
import step.learning.services.mime.MimeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@WebServlet("/register")
@MultipartConfig
@Singleton
public class RegisterServlet extends HttpServlet {
    @Inject
    private UserDAO userDAO;
    @Inject
    private MimeService mimeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        String regError = (String) session.getAttribute("regError");
        if (regError != null) {
            req.setAttribute("regError", regError);
            session.removeAttribute("regError");
        }

        String username = (String) session.getAttribute("username");
        if (username != null) {
            req.setAttribute("username", username);
            session.removeAttribute("username");
        }

        String name = (String) session.getAttribute("name");
        if (name != null) {
            req.setAttribute("name", name);
            session.removeAttribute("name");
        }

        String email = (String) session.getAttribute("email");
        if (email != null) {
            req.setAttribute("email", email);
            session.removeAttribute("email");
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
        String email = req.getParameter("email");
        String userId;

        try {
            // region validation
            if (username == null || username.isEmpty()) {
                throw new Exception("Empty username not allowed");
            }
            if (username.contains(" ")) {
                throw new Exception("Spaces in username not allowed");
            }
            if (!userDAO.isUsernameUnique(username)) {
                throw new Exception("Username already in use");
            }
            if (!password.equals(confirmPassword)) {
                throw new Exception("Passwords don't match");
            }
            if (!EmailValidator.getInstance().isValid(email)) {
                throw new Exception("Invalid email");
            }
            // endregion

            // region Uploading
            Part avatar = req.getPart("avatar");
            String avatarName = null;
            if (avatar.getSize() > 0) {
                String fileName = avatar.getSubmittedFileName();
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex == -1) {
                    throw new Exception("Files without extension now allowed");
                }
                String extension = fileName.substring(dotIndex);
                if (!mimeService.isImage(extension)) {
                    throw new Exception("Unsupported file type: " + extension);
                }
                avatarName = UUID.randomUUID() + extension;
                String path = req.getServletContext().getRealPath("/");
                File uploaded = new File(path + "/../Uploads/" + avatarName);
                Files.copy(avatar.getInputStream(), uploaded.toPath());
            }
            // endregion

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setAvatar(avatarName);
            user.setEmail(email);
            if ((userId = userDAO.add(user)) == null) {
                throw new Exception("Server error, try again later");
            }
        } catch (Exception ex) {
            session.setAttribute("regError", ex.getMessage());
            session.setAttribute("username", username);
            session.setAttribute("name", name);
            session.setAttribute("email", email);
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        session.setAttribute("userId", userId);
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        User user = (User) req.getAttribute("user");
        User editedUser = new User();
        editedUser.setId(user.getId());
        editedUser.setName(req.getParameter("name"));
        String username = req.getParameter("username");
        String email = req.getParameter("email");

        if (username != null) {
            if (!userDAO.isUsernameUnique(username)) {
                message = "Username " + username + " already in use";
                resp.getWriter().print(message);
                return;
            }
            editedUser.setUsername(username);
        }

        if (email != null) {
            if(!EmailValidator.getInstance().isValid(email)) {
                message = "Invalid email: " + email;
                resp.getWriter().print(message);
                return;
            }
            editedUser.setEmail(email);
        }

        if (userDAO.update(editedUser)) {
            message = "Success";
        } else {
            message = "Fail";
        }

        resp.getWriter().print(message);
    }
}