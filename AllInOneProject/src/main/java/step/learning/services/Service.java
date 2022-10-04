package step.learning.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.inject.Named;

@Singleton
public class Service {
    @Inject
    private RandomNumberProvider random;
    @Inject
    @Named("DigitProvider")
    private SymbolService symbolService;

    public String getString() {
        return "Hello World " + random.getNumber() + " " + symbolService.getSymbol();
    }
}