package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 */
interface Action {
    
    /**
     * Perform the action on the parser.
     */
    void perform();
    
}
