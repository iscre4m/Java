package step.learning.servlets;

import com.google.inject.Singleton;
import step.learning.services.data.DataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FiltersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataService dataService = (DataService) req.getAttribute("DataService");
        List<String> usernames = new ArrayList<>();
        try (Statement statement = dataService.getConnection().createStatement();
             ResultSet res = statement.executeQuery("SELECT * FROM users")
        ) {
            while (res.next()) {
                usernames.add(res.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        req.setAttribute("usernames", usernames.toArray(new String[0]));
        req.setAttribute("pageBody", "filters.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }
}