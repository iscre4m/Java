package step.learning.servlets;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.ProductDAO;
import step.learning.dao.UserDAO;
import step.learning.entities.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ProductsServlet extends HttpServlet {
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    @Inject
    public ProductsServlet(ProductDAO productDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Multimap<String, Product> usersToProducts = ArrayListMultimap.create();
        String username;

        for (Product product : productDAO.getAll()) {
            username = userDAO.getUserById(product.getUserId()).getUsername();
            usersToProducts.put(username, product);
        }

        req.setAttribute("usersToProducts", usersToProducts);
        req.setAttribute("pageBody", "products.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }
}