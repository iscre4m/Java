package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class AuthFilter implements Filter {

    private FilterConfig filterConfig;
    private final UserDAO userDAO;

    @Inject
    public AuthFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String logout = request.getParameter("logout");

        if (logout != null) {
            if ("navbar-logout".equals(logout)) {

                session.removeAttribute("userId");

                response.sendRedirect("http://localhost:8080/WebProject/");
                return;
            }
        }

        if (request.getMethod().equalsIgnoreCase("POST")) {
            if ("navbar-auth-form".equals(request.getParameter("auth-form"))) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                User user = userDAO.getUserByCredentials(username, password);

                if (user == null) {
                    session.setAttribute("authError", "Incorrect username or password");
                } else {
                    session.setAttribute("userId", user.getId());
                }

                response.sendRedirect("http://localhost:8080/WebProject/");
                return;
            }
        }

        String authError = (String) session.getAttribute("authError");
        String userId = (String) session.getAttribute("userId");

        if (authError != null) {
            request.setAttribute("authError", authError);
            session.removeAttribute("authError");
        }
        if (userId != null) {
            request.setAttribute("userId", userId);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}