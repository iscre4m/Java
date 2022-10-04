package step.learning;

import com.google.inject.Guice;
import step.learning.services.ConfigModule;

public class Main {
    public static void main(String[] args) {
        Guice.createInjector(new ConfigModule()).getInstance(App.class).run();
    }
}