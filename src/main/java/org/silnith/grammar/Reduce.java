package org.silnith.grammar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Apply a production reduction to the stack.  This removes the symbols for each element of the production,
 * passes them through the production handler, and puts the output of the production handler onto the stack.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Reduce<T extends TerminalSymbol> implements Action<T> {
    
    private final LookaheadItem<T> reduceItem;
    
    public Reduce(final LookaheadItem<T> reduceItem) {
        super();
        if (reduceItem == null) {
            throw new IllegalArgumentException();
        }
        this.reduceItem = reduceItem;
    }
    
    @Override
    public boolean perform(final ParserData<T> data) {
        final Item item = reduceItem.getItem();
        final NonTerminalSymbol targetNonTerminal = item.getTarget();
        final Production production = item.getProduction();
        final List<Symbol> symbols = production.getSymbols();
        final Deque<Object> data1 = new ArrayDeque<>(symbols.size());
        for (@SuppressWarnings("unused") final Symbol symbol : symbols) {
            data.popState();
            final Object datum = data.popData();
            data1.addFirst(datum);
        }
        final ProductionHandler handler = production.getProductionHandler();
        final Object newDatum = handler.handleReduction(new ArrayList<>(data1));
        data.setState(data.peekState());
        
        assert data.getActions(targetNonTerminal).size() == 1;
        
        final Action<T> gotoAction = data.getAction(targetNonTerminal);
        assert gotoAction instanceof Goto;
        gotoAction.perform(data);
        data.pushState();
        data.pushData(newDatum);
        return false;
    }

    @Override
    public String toString() {
        return "Reduce(" + reduceItem + ")";
    }
    
}