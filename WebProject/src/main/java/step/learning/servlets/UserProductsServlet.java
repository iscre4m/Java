package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.ProductDAO;
import step.learning.entities.Product;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Singleton
public class UserProductsServlet extends HttpServlet {
    private final ProductDAO productDAO;

    @Inject
    public UserProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        List<Product> products = null;
        String pageBody = "user_products.jsp";

        if (user == null) {
            pageBody = "not_authorized.jsp";
        } else {
            products = productDAO.getByUserId(user.getId());
        }

        req.setAttribute("products", products);
        req.setAttribute("pageBody", pageBody);
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = req.getParameter("method");
        String productId = req.getParameter("product-id");

        switch (method) {
            case "POST" -> resp.sendRedirect(req.getContextPath() + "/product");
            case "PUT" -> resp.sendRedirect(req.getContextPath() + "/product?id=" + productId);
            case "DELETE" -> {
                productDAO.removeById(productId);
                resp.sendRedirect(req.getContextPath() + "/products");
            }
        }
    }
}