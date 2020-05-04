package org.silnith.grammar;

/**
 * The parser consumes an additional terminal symbol.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Shift<T extends TerminalSymbol> extends AbstractAction<T> {
    
    private final ParserState<T> destinationState;
    
    public Shift(final Parser<T> parser, final ParserState<T> destinationState) {
        super(parser);
        if (destinationState == null) {
            throw new IllegalArgumentException();
        }
        this.destinationState = destinationState;
    }
    
    @Override
    public void perform() {
        getParser().shift(destinationState);
    }

    @Override
    public String toString() {
        return "Shift(" + destinationState + ")";
    }
    
}