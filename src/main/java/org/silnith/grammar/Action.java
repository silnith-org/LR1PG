package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 */
interface Action<T extends TerminalSymbol> {
    
    /**
     * Perform the action on the parser.
     * 
     * @return the new parser state data
     */
    ParserData<T> perform(ParserData<T> data);
    
}
