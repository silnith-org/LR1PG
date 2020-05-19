package org.silnith.grammar;

/**
 * The parser changes state without otherwise modifying the stack.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Goto<T extends TerminalSymbol> extends AbstractAction<T> {
    
    private final ParserState<T> destinationState;
    
    public Goto(final Parser<T> parser, final ParserState<T> destinationState) {
        super();
        if (destinationState == null) {
            throw new IllegalArgumentException();
        }
        this.destinationState = destinationState;
    }
    
    @Override
    public boolean perform(final ParserData<T> data) {
        data.setState(destinationState);
        return false;
    }

    @Override
    public String toString() {
        return "Goto(" + destinationState + ")";
    }
    
}