package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.*;
import java.io.IOException;

@Singleton
public class EmailStatusFilter implements Filter {
    private FilterConfig filterConfig;
    @Inject
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        String userId = servletRequest.getParameter("userId");
        User user = userId == null
                ? (User) servletRequest.getAttribute("user")
                : userDAO.getUserById(userId);
        if (user != null) {
            servletRequest.setAttribute("confirmationNeeded", !userDAO.isEmailConfirmed(user));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}