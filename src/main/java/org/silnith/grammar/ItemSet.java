package org.silnith.grammar;

import java.util.HashMap;
import java.util.Map;
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

    private int conflictCount = 0;
    private final Map<Symbol, Action<T>> parsingTable = new HashMap<>();
    
    /**
     * Adds an action to the parse table.
     * 
     * @param parserState the current parser state
     * @param symbol the next symbol to be consumed
     * @param action the parser action to take
     */
    public void putAction(final Symbol symbol, final Action<T> action) {
        final Action<T> previousAction = parsingTable.put(symbol, action);
        if (previousAction != null) {
            conflictCount++;
            System.out.println("Action conflict #" + conflictCount);
//            System.out.println(parserState);
            printLong();
            System.out.println(symbol);
            System.out.println(previousAction);
            System.out.println(action);
            
            throw new IllegalStateException("Conflict between actions " + action + " and " + previousAction);
        }
    }
    
    public Action<T> getAction(final Symbol symbol) {
        final Action<T> action = parsingTable.get(symbol);
        if (action == null) {
            printLong();
            System.out.print("Next symbol: ");
            System.out.println(symbol);
            throw new IllegalStateException(
                    "No parse action for symbol: " + symbol + " and state: " + this);
        }
        return action;
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
