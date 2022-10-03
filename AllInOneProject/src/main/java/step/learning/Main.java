package step.learning;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        injector.getInstance(App.class)
                .run();
    }
}