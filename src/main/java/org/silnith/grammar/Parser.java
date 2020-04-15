package org.silnith.grammar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A parser for the language defined by the grammar.  The generated parser is guaranteed to process any input stream
 * of terminal symbols in {@code O(n)} time.
 * 
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class Parser<T extends TerminalSymbolMatch> {
	
    private final Map<ItemSet<T>, String> parserStateNames;
    
    private final Set<Edge<T>> edges;
    
    private final ItemSet<T> startState;
    
    private final T endOfFileSymbol;
    
    private final ParsingStateTable<ItemSet<T>, SymbolMatch, Action> parsingTable;
    
    private final Deque<SymbolMatch> symbolMatchStack;
    
    private final Deque<ItemSet<T>> stateStack;
    
    private final Deque<DataStackElement> dataStack;
    
    public Parser(final Set<ItemSet<T>> parserStates, final Set<Edge<T>> edges, final ItemSet<T> startState,
            final T endOfFileSymbol) {
        super();
        if (parserStates == null || edges == null || startState == null || endOfFileSymbol == null) {
        	throw new IllegalArgumentException();
        }
        this.parserStateNames = new HashMap<>(parserStates.size());
        int i = 1;
        for (final ItemSet<T> state : parserStates) {
            this.parserStateNames.put(state, "state" + i);
            i++ ;
        }
        this.edges = edges;
        this.startState = startState;
        this.endOfFileSymbol = endOfFileSymbol;
        this.parsingTable = new ParsingStateTable<>();
        this.symbolMatchStack = new ArrayDeque<>();
        this.stateStack = new ArrayDeque<>();
        this.dataStack = new ArrayDeque<>();
        
        this.calculateParseTable();
    }
    
    public void calculateParseTable() {
        for (final Edge<T> edge : edges) {
            final ItemSet<T> parserState = edge.getInitialState();
            final SymbolMatch symbol = edge.getSymbol();
            final ItemSet<T> destinationState = edge.getFinalState();
            final Action action;
            if (symbol instanceof TerminalSymbolMatch) {
                final Shift<T> shiftAction = new Shift<>(parserState, symbol, destinationState);
                action = shiftAction;
            } else if (symbol instanceof NonTerminalSymbolMatch) {
                final Goto<T> gotoAction = new Goto<>(parserState, symbol, destinationState);
                action = gotoAction;
            } else {
                throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
            }
            putAction(parserState, symbol, action);
        }
        for (final ItemSet<T> parserState : parserStateNames.keySet()) {
            for (final LookaheadItem<T> item : parserState.getItems()) {
                if (item.isComplete()) {
                    for (final T lookahead : item.getLookaheadSet()) {
                        putAction(parserState, lookahead, new Reduce<>(parserState, lookahead, item));
                    }
                } else {
                    final SymbolMatch symbol = item.getNextSymbol();
                    if (symbol.equals(endOfFileSymbol)) {
                        putAction(parserState, symbol, new Accept(parserState, symbol));
                    }
                }
            }
        }
//        parsingTable.printTableLong();
    }
    
    private int conflictCount = 0;
    
    /**
     * Adds an action to the parse table.
     * 
     * @param parserState the current parser state
     * @param symbol the next symbol to be consumed
     * @param action the parser action to take
     */
    protected void putAction(final ItemSet<T> parserState, final SymbolMatch symbol, final Action action) {
        final Action previousAction = parsingTable.put(parserState, symbol, action);
        if (previousAction != null) {
        	conflictCount++;
            System.out.println("Action conflict #" + conflictCount);
//            System.out.println(parserState);
            parserState.printLong();
            System.out.println(symbol);
            System.out.println(previousAction);
            System.out.println(action);
            
            throw new IllegalStateException("Conflict between actions " + action + " and " + previousAction);
        }
    }
    
    protected String getName(final ItemSet<T> state) {
        return parserStateNames.get(state);
    }
    
    private Terminal currentSymbol;
    
    private Terminal lookahead;
    
    protected Terminal getNextSymbol(final Iterator<Terminal> iterator) {
        if (currentSymbol == null) {
            currentSymbol = iterator.next();
        } else {
            currentSymbol = lookahead;
        }
        if (iterator.hasNext()) {
            lookahead = iterator.next();
        } else {
            lookahead = new Terminal() {
                
                @Override
                public T getMatch() {
                    return endOfFileSymbol;
                }
                
            };
        }
        return currentSymbol;
    }
    
    protected Terminal getLookahead() {
        return lookahead;
    }
    
    /**
     * Parses a sequence of terminal symbols and returns an abstract syntax tree.  This runs in {@code O(n)} time.
     * 
     * @param lexer the lexer that generates an input sequence of terminal symbols
     * @return an abstract syntax tree as constructed by the various {@link ProductionHandler} implementations used in
     *         the {@link Grammar}
     */
    public Object parse(final Lexer lexer) {
    	currentSymbol = null;
    	lookahead = null;
        ItemSet<T> currentState = startState;
        stateStack.push(currentState);
        final Iterator<Terminal> iterator = lexer.iterator();
        Terminal nextSymbol = getNextSymbol(iterator);
        boolean done = false;
        do {
            final Action action = parsingTable.get(currentState, nextSymbol.getMatch());
            if (action == null) {
                currentState.printLong();
                System.out.print("Next symbol: ");
                System.out.println(nextSymbol);
                throw new IllegalStateException(
                        "No parse action for symbol: " + nextSymbol + " and state: " + getName(currentState));
            }
            switch (action.getType()) {
            case SHIFT: {
                final Shift<T> shiftAction = (Shift<T>) action;
                currentState = shiftAction.getDestinationState();
                symbolMatchStack.push(nextSymbol.getMatch());
                stateStack.push(currentState);
                dataStack.push(new DataStackElement(nextSymbol));
                nextSymbol = getNextSymbol(iterator);
            }
                break;
            case REDUCE: {
                final Reduce<T> reduceAction = (Reduce<T>) action;
                final LookaheadItem<T> reduceItem = reduceAction.getReduceItem();
                final NonTerminalSymbolMatch leftHandSide = reduceItem.getLeftHandSide();
                final Production production = reduceItem.getRightHandSide();
                final List<DataStackElement> data = new LinkedList<>();
                for (@SuppressWarnings("unused")
                final SymbolMatch symbol : production.getSymbols()) {
                    symbolMatchStack.pop();
                    stateStack.pop();
                    final DataStackElement datum = dataStack.pop();
                    data.add(0, datum);
                }
                final ProductionHandler handler = production.getProductionHandler();
                final DataStackElement newDatum = new DataStackElement(handler.handleReduction(data));
                currentState = stateStack.peek();
                final Action tempAction = parsingTable.get(currentState, leftHandSide);
                assert tempAction instanceof Goto;
                final Goto<T> gotoAction = (Goto<T>) tempAction;
                currentState = gotoAction.getDestinationState();
                symbolMatchStack.push(leftHandSide);
                stateStack.push(currentState);
                dataStack.push(newDatum);
            }
                break;
            case GOTO: {
                final Goto<T> gotoAction = (Goto<T>) action;
                currentState = gotoAction.getDestinationState();
            }
                break;
            case ACCEPT: {
                done = true;
                System.out.println("Accept.");
            }
                break;
            default: {
                throw new IllegalStateException("Unknown action: " + action);
            } // break;
            }
        } while ( !done);
        return dataStack.getLast().getAbstractSyntaxTreeElement();
    }
    
}
