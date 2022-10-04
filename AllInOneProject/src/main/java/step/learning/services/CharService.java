package step.learning.services;

import javax.inject.Named;

@Named("CharProvider")
public class CharService implements SymbolService {
    @Override
    public char getSymbol() {
        return 'ඞ';
    }
}