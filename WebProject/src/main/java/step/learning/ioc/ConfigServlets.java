package step.learning.ioc;

import com.google.inject.servlet.ServletModule;
import step.learning.filters.CharsetFilter;
import step.learning.filters.DataFilter;
import step.learning.filters.DemoFilter;
import step.learning.servlets.FiltersServlet;
import step.learning.servlets.HashServlet;
import step.learning.servlets.HomeServlet;
import step.learning.servlets.RegisterServlet;

public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/*").through(CharsetFilter.class);
        filter("/*").through(DataFilter.class);
        filter("/*").through(DemoFilter.class);

        serve("/register").with(RegisterServlet.class);
        serve("/filter").with(FiltersServlet.class);
        serve("/hash").with(HashServlet.class);
        serve("/").with(HomeServlet.class);
    }
}