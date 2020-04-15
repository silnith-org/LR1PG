package org.silnith.grammar;

/**
 * The parser consumes an additional terminal symbol.
 * 
 * @param <T> the type of terminal symbols
 */
public class Shift<T extends TerminalSymbolMatch> extends Action {
    
    private final ItemSet<T> destinationState;
    
    public Shift(final ItemSet<T> sourceState, final SymbolMatch symbol, final ItemSet<T> destinationState) {
        super(sourceState, symbol);
        if (destinationState == null) {
        	throw new IllegalArgumentException();
        }
        this.destinationState = destinationState;
    }
    
    @Override
    public Type getType() {
        return Type.SHIFT;
    }
    
    public ItemSet<T> getDestinationState() {
        return destinationState;
    }
    
    @Override
    public String toString() {
        return "Shift(" + destinationState + ")";
    }
    
}
