package org.silnith.grammar;

class EdgeFactory<T extends TerminalSymbol> extends WeakCanonicalFactory<Edge<T>> {
    
    public Edge<T> createInstance(final ParserState<T> initialState, final Symbol symbol, final ParserState<T> finalState) {
        return valueOf(new Edge<>(initialState, symbol, finalState));
    }
    
}
