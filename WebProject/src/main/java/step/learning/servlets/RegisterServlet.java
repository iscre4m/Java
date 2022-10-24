package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/register")
@MultipartConfig
@Singleton
public class RegisterServlet extends HttpServlet {
    @Inject
    private UserDAO userDAO;

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
                throw new Exception("Username already in use");
            }
            if (!password.equals(confirmPassword)) {
                throw new Exception("Passwords don't match");
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
                if (!Arrays.asList(new String[]{".jpg", ".png", ".bmp"})
                        .contains(extension)) {
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
            if (userDAO.add(user) == null) {
                throw new Exception("Server error, try again later");
            }
        } catch (Exception ex) {
            session.setAttribute("regError", ex.getMessage());
            session.setAttribute("username", username);
            session.setAttribute("name", name);
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        session.setAttribute("regOk", "Registration successful");
        resp.sendRedirect(req.getContextPath());
    }
}