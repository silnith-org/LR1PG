package org.silnith.grammar;

/**
 * The elements that compose the language defined by a grammar.
 * They are divided into two types, {@link Type#TERMINAL} and
 * {@link Type#NON_TERMINAL}.  Terminal and non-terminal symbols
 * do not need to share an inheritance hierarchy, they simply
 * need to implement the appropriate sub-interface.
 * 
 * @see TerminalSymbol
 * @see NonTerminalSymbol
 */
public interface Symbol {
    
    /**
     * Represents the type of the symbol.
     */
    public enum Type {
        /**
         * A terminal symbol.
         * 
         * @see TerminalSymbol
         */
        TERMINAL,
        /**
         * A non-terminal symbol.
         * 
         * @see NonTerminalSymbol
         */
        NON_TERMINAL
    }
    
    /**
     * Returns the type of the symbol.
     * 
     * @return the type of the symbol
     */
    Type getType();
    
}
