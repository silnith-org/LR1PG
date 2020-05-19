package org.silnith.grammar;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a parser state.  This is a set of items, where each item is a partially-completed production
 * coupled with a look-ahead set.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class ParserState<T extends TerminalSymbol> {
    
    private final Set<LookaheadItem<T>> itemSet;
    
    private final int hashCode;

    private final Map<Symbol, Action> parsingTable;

    private final Map<Symbol, Set<Action>> parsingTable2;

    public ParserState(final Set<LookaheadItem<T>> items) {
        super();
        if (items == null) {
            throw new IllegalArgumentException();
        }
        this.itemSet = Collections.unmodifiableSet(items);
        this.hashCode = Objects.hash(this.itemSet);
        /*
         * The parsing table is not part of a state's "identity" and so is not included in the
         * hash code calculation or the equals comparison.
         */
        this.parsingTable = new HashMap<>();
        this.parsingTable2 = new HashMap<>();
    }
    
    public Set<LookaheadItem<T>> getItems() {
        return itemSet;
    }
    
    private int conflictCount = 0;
    
    public void initializeParseTable(final Collection<T> terminals, final Collection<NonTerminalSymbol> nonTerminals) {
        for (final T terminal : terminals) {
            parsingTable2.put(terminal, new HashSet<Action>());
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminals) {
            parsingTable2.put(nonTerminalSymbol, new HashSet<Action>());
        }
    }
    
    /**
     * Adds an action to the parse table.
     * 
     * @param parserState the current parser state
     * @param symbol the next symbol to be consumed
     * @param action the parser action to take
     */
    public void putAction(final Symbol symbol, final Action action) {
        parsingTable2.get(symbol).add(action);
        final Action previousAction = parsingTable.put(symbol, action);
        if (previousAction != null) {
            conflictCount++;
//            System.out.println("Action conflict #" + conflictCount);
//            printLong();
//            System.out.println(symbol);
//            System.out.println(previousAction);
//            System.out.println(action);
            
            throw new IllegalStateException("Conflict between actions " + action + " and " + previousAction);
        }
    }
    
    public Set<Action> getActions(final Symbol symbol) {
        return parsingTable2.get(symbol);
    }
    
    public Action getAction(final Symbol symbol) {
        final Action action = parsingTable.get(symbol);
        if (action == null) {
//            printLong();
//            System.out.print("Next symbol: ");
//            System.out.println(symbol);
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
        if (obj instanceof ParserState) {
            final ParserState<?> other = (ParserState<?>) obj;
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
