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
import javax.servlet.http.HttpSession;
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

        HttpSession session = req.getSession();
        Boolean nameIsBlank = (Boolean) session.getAttribute("nameIsBlank");
        Boolean descriptionIsBlank = (Boolean) session.getAttribute("descriptionIsBlank");
        Boolean priceIsBlank = (Boolean) session.getAttribute("priceIsBlank");
        Boolean priceIsInvalid = (Boolean) session.getAttribute("priceIsInvalid");
        boolean error = false;

        if (nameIsBlank != null) {
            req.setAttribute("nameIsBlank", true);
            session.removeAttribute("nameIsBlank");
            error = true;
        }
        if (descriptionIsBlank != null) {
            req.setAttribute("descriptionIsBlank", true);
            session.removeAttribute("descriptionIsBlank");
            error = true;
        }
        if (priceIsBlank != null) {
            req.setAttribute("priceIsBlank", true);
            session.removeAttribute("priceIsBlank");
            error = true;
        }
        if (priceIsInvalid != null) {
            req.setAttribute("priceIsInvalid", true);
            session.removeAttribute("priceIsInvalid");
            error = true;
        }

        req.setAttribute("name", session.getAttribute("name"));
        session.removeAttribute("name");
        req.setAttribute("description", session.getAttribute("description"));
        session.removeAttribute("description");
        req.setAttribute("priceString", session.getAttribute("priceString"));
        session.removeAttribute("priceString");

        req.setAttribute("buttonText", "Add");
        if (!error) {
            String productId = req.getParameter("id");
            Product product;

            if (productId != null) {
                product = productDAO.getById(productId);
                if (product != null) {
                    req.setAttribute("id", product.getId());
                    req.setAttribute("buttonText", "Edit");
                    req.setAttribute("name", product.getName());
                    req.setAttribute("description", product.getDescription());
                    req.setAttribute("price", product.getPrice());
                }
            }
        }

        req.setAttribute("pageBody", "product.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = ((User) req.getAttribute("user")).getId();
        String productId = req.getParameter("id");
        String method = req.getParameter("method");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceString = req.getParameter("price");
        boolean error = false;

        if (name.isBlank()) {
            session.setAttribute("nameIsBlank", true);
            error = true;
        }
        if (description.isBlank()) {
            session.setAttribute("descriptionIsBlank", true);
            error = true;
        }
        if (priceString.isBlank()) {
            session.setAttribute("priceIsBlank", true);
            error = true;
        }
        if (!priceString.matches("^(0|[1-9][0-9]{0,2})(,\\d{3})*(\\.\\d{1,2})?$")) {
            session.setAttribute("priceIsInvalid", true);
            error = true;
        }

        if (error) {
            session.setAttribute("name", name);
            session.setAttribute("description", description);
            session.setAttribute("priceString", priceString);
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        Product product = new Product();

        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(priceString));
        product.setUserId(userId);

        switch (method) {
            case "POST" -> productDAO.add(product);
            case "PUT" -> productDAO.update(product);
        }

        resp.sendRedirect(req.getContextPath() + "/products");
    }
}