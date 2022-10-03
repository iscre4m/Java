package step.learning;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Service {
    @Inject
    private RandomNumberProvider random;

    public String getString() {
        return "Hello World " + random.getNumber();
    }
}