package org.silnith.grammar;

import java.util.Objects;

public class Edge<T extends TerminalSymbol> {
    
    private final ParserState<T> initialState;
    
    private final Symbol symbol;
    
    private final ParserState<T> finalState;
    
    private final int hashCode;
    
    public Edge(final ParserState<T> initialState, final Symbol symbol, final ParserState<T> finalState) {
        super();
        if (initialState == null || symbol == null || finalState == null) {
            throw new IllegalArgumentException();
        }
        this.initialState = initialState;
        this.symbol = symbol;
        this.finalState = finalState;
        this.hashCode = Objects.hash(this.initialState, this.symbol, this.finalState);
    }
    
    public ParserState<T> getInitialState() {
        return initialState;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    public ParserState<T> getFinalState() {
        return finalState;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Edge) {
            final Edge<?> other = (Edge<?>) obj;
            return initialState.equals(other.initialState) && symbol.equals(other.symbol)
                    && finalState.equals(other.finalState);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return initialState + " --> (" + symbol + ") --> " + finalState;
    }
    
}
