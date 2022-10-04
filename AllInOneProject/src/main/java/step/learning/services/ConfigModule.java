package step.learning.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import javax.inject.Named;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RandomNumberProvider.class).to(RandomIntProvider.class);

        bind(SymbolService.class)
                .annotatedWith(Names.named("CharProvider"))
                .to(CharService.class);
        bind(SymbolService.class)
                .annotatedWith(Names.named("DigitProvider"))
                .to(DigitService.class);

        bind(HashService.class)
                .annotatedWith(Names.named("SHA1Service"))
                .to(SHA1Service.class);
        bind(HashService.class)
                .annotatedWith(Names.named("MD5Service"))
                .to(MD5Service.class);
    }

    @Provides
    @Named("MSSQL")
    String mssqlConnectionString() {
        return "MSSQL Connection String";
    }

    @Provides
    @Named("OracleSQL")
    String oracleSqlConnectionString() {
        return "OracleSQL Connection String";
    }
}