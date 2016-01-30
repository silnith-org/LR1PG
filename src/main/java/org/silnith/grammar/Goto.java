package org.silnith.grammar;

public class Goto<T> extends Action<T> {
    
    private final ItemSet<T> destinationState;
    
    public Goto(final ItemSet<T> sourceState, final Symbol symbol, final ItemSet<T> destinationState) {
        super(sourceState, symbol);
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
