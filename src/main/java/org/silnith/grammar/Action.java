package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 * 
 * @param <T> the type of terminal symbols
 */
public interface Action<T extends TerminalSymbol> {
    
    /**
     * Perform the action on the parser.
     */
    void perform();
    
}
