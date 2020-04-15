package org.silnith.grammar;

/**
 * The parser replaces some symbols on the top of the stack with a new symbol.
 * 
 * @param <T> the type of terminal symbols
 */
public class Reduce<T extends TerminalSymbolMatch> extends Action {
    
    private final LookaheadItem<T> reduceItem;
    
    public Reduce(final ItemSet<T> sourceState, final SymbolMatch symbol, final LookaheadItem<T> reduceItem) {
        super(sourceState, symbol);
        if (reduceItem == null) {
        	throw new IllegalArgumentException();
        }
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
