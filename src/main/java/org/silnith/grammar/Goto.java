package org.silnith.grammar;

/**
 * The parser changes state without otherwise modifying the stack.
 */
class Goto<T extends TerminalSymbol> implements Action {
    
    private final Parser<T> parser;
    
    private final ParserState<T> destinationState;
    
    public Goto(final Parser<T> parser, final ParserState<T> destinationState) {
        super();
        if (destinationState == null) {
            throw new IllegalArgumentException();
        }
        this.parser = parser;
        this.destinationState = destinationState;
    }
    
    @Override
    public void perform() {
        parser.goTo(destinationState);
    }

    @Override
    public String toString() {
        return "Goto(" + destinationState + ")";
    }
    
}