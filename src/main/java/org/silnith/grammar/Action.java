package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 * 
 * @param <T> the type of terminal symbols
 */
public interface Action<T extends TerminalSymbol> {
    
    /**
     * The type of action to take.
     */
    enum Type {
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
    
    ItemSet<T> getSourceState();
    
    Symbol getSymbol();
    
    Type getType();
    
    /**
     * Perform the action on the parser.
     */
    void perform();
    
}
