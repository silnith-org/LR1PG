package org.silnith.grammar;

import java.util.Set;


public class LookaheadItem<T> {
    
    private final Item item;
    
    private final Set<T> lookaheadSet;
    
    private final int hashCode;
    
    public LookaheadItem(final Item item, final Set<T> lookaheadSet) {
        super();
        this.item = item;
        this.lookaheadSet = lookaheadSet;
        this.hashCode = this.item.hashCode() ^ this.lookaheadSet.hashCode();
    }
    
    public Item getItem() {
        return item;
    }
    
    public boolean isComplete() {
        return item.isComplete();
    }
    
    public Symbol getNextSymbol() {
        return item.getNextSymbol();
    }
    
    public NonTerminalSymbol getLeftHandSide() {
        return item.getLeftHandSide();
    }
    
    public Production getRightHandSide() {
        return item.getRightHandSide();
    }
    
    public int getParserPosition() {
        return item.getParserPosition();
    }
    
    public Set<T> getLookaheadSet() {
        return lookaheadSet;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LookaheadItem) {
            @SuppressWarnings("unchecked")
            final LookaheadItem<?> other = (LookaheadItem<Object>) obj;
            if (hashCode != other.hashCode) {
                return false;
            }
            return item.equals(other.item) && lookaheadSet.equals(other.lookaheadSet);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "{item: " + item + ", lookahead set: " + lookaheadSet + "}";
    }
    
}
