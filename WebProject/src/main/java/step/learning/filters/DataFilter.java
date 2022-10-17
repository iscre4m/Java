package step.learning.filters;

import step.learning.services.DataService;
import step.learning.services.MySQLDataService;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;

public class DataFilter implements Filter {
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
        DataService dataService = new MySQLDataService();
        Connection connection = dataService.getConnection();

        if (connection == null) {
            servletRequest.getRequestDispatcher("WEB-INF/static.jsp")
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