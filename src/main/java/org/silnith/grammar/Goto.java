package org.silnith.grammar;

/**
 * The parser changes state without otherwise modifying the stack.
 * 
 * @param <T> the type of terminal symbols
 */
public class Goto<T extends TerminalSymbolMatch> extends Action {
    
    private final ItemSet<T> destinationState;
    
    public Goto(final ItemSet<T> sourceState, final SymbolMatch symbol, final ItemSet<T> destinationState) {
        super(sourceState, symbol);
        if (destinationState == null) {
        	throw new IllegalArgumentException();
        }
        this.destinationState = destinationState;
    }
    
    @Override
    public Type getType() {
        return Type.GOTO;
    }
    
    public ItemSet<T> getDestinationState() {
        return destinationState;
    }
    
    @Override
    public String toString() {
        return "Goto(" + destinationState + ")";
    }
    
}
