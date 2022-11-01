package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import step.learning.services.data.DataService;
import step.learning.services.data.MySQLDataService;
import step.learning.services.email.EmailService;
import step.learning.services.email.GmailService;
import step.learning.services.hash.HashService;
import step.learning.services.hash.MD5HashService;
import step.learning.services.hash.SHA1HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(MySQLDataService.class);
        bind(HashService.class)
                .annotatedWith(Names.named("MD5HashService"))
                .to(MD5HashService.class);
        bind(HashService.class)
                .annotatedWith(Names.named("SHA1HashService"))
                .to(SHA1HashService.class);
        bind(EmailService.class).to(GmailService.class);
    }
}