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
import java.math.BigDecimal;

@Singleton
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO;

    @Inject
    public ProductServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getAttribute("user");

        if (user == null) {
            req.setAttribute("pageBody", "not_authorized.jsp");
            req.getRequestDispatcher("WEB-INF/_layout.jsp")
                    .forward(req, resp);
            return;
        }

        String productId = req.getParameter("id");
        Product product = null;

        if (productId != null) {
            product = productDAO.getById(productId);
        }

        req.setAttribute("product", product);
        req.setAttribute("pageBody", "product.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = ((User) req.getAttribute("user")).getId();
        String method = req.getParameter("method");
        String productId = req.getParameter("id");
        System.out.println(productId);
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        Product product = new Product();

        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUserId(userId);

        switch (method) {
            case "POST" -> productDAO.add(product);
            case "PUT" -> productDAO.update(product);
        }

        resp.sendRedirect(req.getContextPath() + "/products");
    }
}