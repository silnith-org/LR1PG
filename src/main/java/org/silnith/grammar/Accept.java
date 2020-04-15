package org.silnith.grammar;

/**
 * The parser accepts the input as a complete "statement" in the language.
 * 
 * @param <T> the type of terminal symbols
 */
public class Accept<T extends TerminalSymbolMatch> extends Action<T> {
    
    /**
     * Creates a new "accept" action.
     * 
     * @param sourceState the source state to accept
     * @param symbol the next symbol to consume.  This should be whatever the end-of-file symbol is.
     */
    public Accept(final ItemSet<T> sourceState, final SymbolMatch symbol) {
        super(sourceState, symbol);
    }
    
    @Override
    public Type getType() {
        return Type.ACCEPT;
    }
    
    @Override
    public String toString() {
        return "Accept";
    }
    
}
