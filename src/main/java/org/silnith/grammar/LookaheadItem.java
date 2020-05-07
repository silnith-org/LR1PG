package org.silnith.grammar;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;


/**
 * An {@link Item} paired with a set of lookahead tokens.
 * 
 * @param <T> the concrete type of terminal symbols
 */
public class LookaheadItem<T extends TerminalSymbol> {
    
    private final Item item;
    
    private final Set<T> lookaheadSet;
    
    private final int hashCode;
    
    public LookaheadItem(final Item item, final Set<T> lookaheadSet) {
        super();
        if (item == null || lookaheadSet == null) {
        	throw new IllegalArgumentException();
        }
        this.item = item;
        this.lookaheadSet = Collections.unmodifiableSet(lookaheadSet);
        this.hashCode = Objects.hash(this.item, this.lookaheadSet);
    }
    
    public Item getItem() {
        return item;
    }
    
    public boolean isComplete() {
        return getItem().isComplete();
    }
    
    public Symbol getNextSymbol() {
        return getItem().getNextSymbol();
    }
    
    public NonTerminalSymbol getTarget() {
        return getItem().getTarget();
    }
    
    public Production getProduction() {
        return getItem().getProduction();
    }
    
    public int getParserPosition() {
        return getItem().getParserPosition();
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
            final LookaheadItem<?> other = (LookaheadItem<?>) obj;
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
