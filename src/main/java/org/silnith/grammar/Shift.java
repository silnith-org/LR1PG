package org.silnith.grammar;

/**
 * The parser must consume an additional terminal symbol.
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
    public boolean perform(final ParserData<T> data) {
        data.setState(destinationState);
        return true;
    }

    @Override
    public String toString() {
        return "Shift(" + destinationState + ")";
    }
    
}