package org.silnith.grammar;

public class Reduce<T> extends Action<T> {
    
    private final LookaheadItem<T> reduceItem;
    
    public Reduce(final ItemSet<T> sourceState, final Symbol symbol, final LookaheadItem<T> reduceItem) {
        super(sourceState, symbol);
        this.reduceItem = reduceItem;
    }
    
    @Override
    public Type getType() {
        return Type.REDUCE;
    }
    
    public LookaheadItem<T> getReduceItem() {
        return reduceItem;
    }
    
    @Override
    public String toString() {
        return "Reduce(" + reduceItem + ")";
    }
    
}
