package org.silnith.grammar;

class FinalToken<T extends TerminalSymbol> implements Token<T> {
    
    private final T endOfFileSymbol;
    
    FinalToken(final T endOfFileSymbol) {
        this.endOfFileSymbol = endOfFileSymbol;
    }
    
    @Override
    public T getSymbol() {
        return endOfFileSymbol;
    }
    
}