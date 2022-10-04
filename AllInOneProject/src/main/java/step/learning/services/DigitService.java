package step.learning.services;

import javax.inject.Named;

@Named("DigitProvider")
public class DigitService implements SymbolService {
    @Override
    public char getSymbol() {
        return '1';
    }
}