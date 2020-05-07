package org.silnith.grammar;

import java.util.Iterator;

class TempLexer<T extends TerminalSymbol> {

    private final Iterator<Token<T>> iterator;
    
    private final Token<T> finalToken;
    
    private Token<T> currentToken;

    private Token<T> lookaheadToken;
    
    public TempLexer(final Iterator<Token<T>> iterator, final Token<T> finalToken) {
        super();
        this.iterator = iterator;
        this.finalToken = finalToken;
    }

    public Token<T> getToken() {
        if (currentToken == null) {
            currentToken = iterator.next();
        } else {
            currentToken = lookaheadToken;
        }
        if (iterator.hasNext()) {
            lookaheadToken = iterator.next();
        } else {
            lookaheadToken = finalToken;
        }
        return currentToken;
    }
    
}