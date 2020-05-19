package org.silnith.grammar;

/**
 * The parser must consume an additional terminal symbol.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Shift<T extends TerminalSymbol> implements Action<T> {
    
    private final ParserState<T> destinationState;
    
    public Shift(final ParserState<T> destinationState) {
        super();
        if (destinationState == null) {
            throw new IllegalArgumentException();
        }
        this.destinationState = destinationState;
    }
    
    @Override
    public ParserData<T> perform(final ParserData<T> data) {
        data.setState(destinationState);
        data.setReadyForShift(true);
        return data;
    }

    @Override
    public String toString() {
        return "Shift(" + destinationState + ")";
    }
    
}