package org.silnith.grammar;

import java.util.Set;


/**
 * Represents a parser state.  This is a set of items, where each item is xxx
 * coupled with a look-ahead set.
 */
public class ItemSet<T extends TerminalSymbol> {
    private final Set<LookaheadItem<T>> itemSet;
    
    private final int hashCode;
    
    public ItemSet(final Set<LookaheadItem<T>> items) {
        super();
        if (items == null) {
        	throw new IllegalArgumentException();
        }
        this.itemSet = items;
        this.hashCode = this.itemSet.hashCode();
    }
    
    public Set<LookaheadItem<T>> getItems() {
        return itemSet;
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
        if (obj instanceof ItemSet) {
            final ItemSet<?> other = (ItemSet<?>) obj;
            if (hashCode != other.hashCode) {
                return false;
            }
            return itemSet.equals(other.itemSet);
        } else {
            return false;
        }
    }
    
    public void printLong() {
        System.out.println("ItemSet:");
        for (final LookaheadItem<T> item : itemSet) {
            System.out.print('\t');
            System.out.println(item);
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(itemSet);
    }
    
}
