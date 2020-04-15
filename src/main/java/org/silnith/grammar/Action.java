package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 * 
 * @param <T> the type of terminal symbols
 */
public abstract class Action<T extends TerminalSymbolMatch> {
    
    /**
     * The type of action to take.
     */
    public enum Type {
        /**
         * The parser consumes an additional terminal symbol.
         * 
         * @see Shift
         */
        SHIFT,
        /**
         * The parser changes state without otherwise modifying the stack.
         * 
         * @see Goto
         */
        GOTO,
        /**
         * The parser replaces some symbols on the top of the stack with a new symbol.
         * 
         * @see Reduce
         */
        REDUCE,
        /**
         * The parser accepts the input as a complete "statement" in the language.
         * 
         * @see Accept
         */
        ACCEPT
    }
    
    private final ItemSet<T> sourceState;
    
    private final SymbolMatch symbol;
    
    public Action(final ItemSet<T> sourceState, final SymbolMatch symbol) {
        super();
        if (sourceState == null || symbol == null) {
        	throw new IllegalArgumentException();
        }
        this.sourceState = sourceState;
        this.symbol = symbol;
    }
    
    public ItemSet<T> getSourceState() {
        return sourceState;
    }
    
    public SymbolMatch getSymbol() {
        return symbol;
    }
    
    public abstract Type getType();
    
}
