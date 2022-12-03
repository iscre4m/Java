package step.learning.ioc;

import com.google.inject.servlet.ServletModule;
import step.learning.filters.*;
import step.learning.servlets.*;

public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/*").through(CharsetFilter.class);
        filter("/*").through(DataFilter.class);
        filter("/*").through(AuthFilter.class);
        filter("/*").through(EmailStatusFilter.class);

        serve("/register").with(RegisterServlet.class);
        serve("/login").with(LoginServlet.class);
        serve("/image/*").with(ImagesServlet.class);
        serve("/profile").with(ProfileServlet.class);
        serve("/confirm").with(ConfirmationServlet.class);
        serve("/products").with(ProductsServlet.class);
        serve("/product").with(ProductServlet.class);
        serve("/user_products").with((UserProductsServlet.class));
        serve("/").with(HomeServlet.class);
    }
}