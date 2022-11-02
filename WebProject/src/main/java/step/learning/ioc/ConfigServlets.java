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
        filter("/*").through(DemoFilter.class);
        filter("/*").through(StatusFilter.class);

        serve("/register").with(RegisterServlet.class);
        serve("/login").with(LoginServlet.class);
        serve("/filter").with(FiltersServlet.class);
        serve("/hash").with(HashServlet.class);
        serve("/image/*").with(ImagesServlet.class);
        serve("/profile").with(ProfileServlet.class);
        serve("/confirm").with(ConfirmationServlet.class);
        serve("/").with(HomeServlet.class);
    }
}