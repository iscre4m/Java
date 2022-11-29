package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.services.data.DataService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class DataFilter implements Filter {
    @Inject
    private DataService dataService;
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        String localUrl = ((HttpServletRequest) servletRequest).getServletPath();
        if ("/img/firefox-logo.png".equalsIgnoreCase(localUrl)
                || "/WEB-INF/static.jsp".equalsIgnoreCase(localUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Connection connection = dataService.getConnection();

        if (connection == null) {
            servletRequest.setAttribute("pageBody", "static.jsp");
            servletRequest.getRequestDispatcher("WEB-INF/_layout.jsp")
                    .forward(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute("DataService", dataService);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}