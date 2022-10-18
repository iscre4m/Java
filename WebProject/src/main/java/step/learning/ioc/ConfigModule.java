package step.learning.ioc;

import com.google.inject.AbstractModule;
import step.learning.services.DataService;
import step.learning.services.MySQLDataService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(MySQLDataService.class);
    }
}