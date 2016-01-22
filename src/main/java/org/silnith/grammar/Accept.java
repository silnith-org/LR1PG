package org.silnith.grammar;

public class Accept<T> extends Action<T> {
    
    public Accept(final ItemSet<T> sourceState, final Symbol symbol) {
        super(sourceState, symbol);
    }
    
    @Override
    public Type getType() {
        return Type.ACCEPT;
    }
    
    @Override
    public String toString() {
        return "Accept";
    }
    
}
