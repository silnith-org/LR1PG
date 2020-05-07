package org.silnith.grammar;

/**
 * The parser consumes an additional terminal symbol.
 */
class Shift<T extends TerminalSymbol> implements Action {
    
    private final Parser<T> parser;
    
    private final ParserState<T> destinationState;
    
    public Shift(final Parser<T> parser, final ParserState<T> destinationState) {
        super();
        if (destinationState == null) {
            throw new IllegalArgumentException();
        }
        this.parser = parser;
        this.destinationState = destinationState;
    }
    
    @Override
    public boolean perform() {
        return parser.shift(destinationState);
    }

    @Override
    public String toString() {
        return "Shift(" + destinationState + ")";
    }
    
}