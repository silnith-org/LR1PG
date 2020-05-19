package org.silnith.grammar;

/**
 * An action to take on consuming a symbol.
 */
interface Action<T extends TerminalSymbol> {
    
    /**
     * Perform the action on the parser.
     * 
     * @return {@code true} if the parser is ready for the next shift
     */
    boolean perform(ParserData<T> data);
    
}
