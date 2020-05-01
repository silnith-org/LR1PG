package org.silnith.grammar;

public class EdgeFactory<T extends TerminalSymbol> extends CanonicalFactory<Edge<T>> {
    
    public Edge<T> createInstance(final ItemSet<T> initialState, final Symbol symbol, final ItemSet<T> finalState) {
        return valueOf(new Edge<>(initialState, symbol, finalState));
    }
    
}
