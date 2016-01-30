package org.silnith.grammar;

public class Shift<T> extends Action<T> {
    
    private final ItemSet<T> destinationState;
    
    public Shift(final ItemSet<T> sourceState, final Symbol symbol, final ItemSet<T> destinationState) {
        super(sourceState, symbol);
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
