package org.silnith.grammar;

public interface Symbol {
    
    public enum Type {
        /**
         * @see TerminalSymbol
         */
        TERMINAL,
        /**
         * @see NonTerminalSymbol
         */
        NON_TERMINAL
    }
    
    Type getType();
    
}
