package org.silnith.grammar;

public abstract class Action<T> {
    
    public enum Type {
        /**
         * @see Shift
         */
        SHIFT,
        /**
         * @see Goto
         */
        GOTO,
        /**
         * @see Reduce
         */
        REDUCE,
        /**
         * @see Accept
         */
        ACCEPT
    }
    
    private final ItemSet<T> sourceState;
    
    private final Symbol symbol;
    
    public Action(final ItemSet<T> sourceState, final Symbol symbol) {
        super();
        this.sourceState = sourceState;
        this.symbol = symbol;
    }
    
    public ItemSet<T> getSourceState() {
        return sourceState;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    public abstract Type getType();
    
}
