package org.silnith.grammar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * A parser for the language defined by a {@link Grammar}.  The generated parser is guaranteed to process any input stream
 * of terminal symbols in {@code O(n)} time.
 * 
 * @param <T> the concrete type of identifiers for terminal symbols
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class Parser<T extends TerminalSymbol> {

    private final Set<ParserState<T>> parserStates;
	
    private final Set<Edge<T>> edges;
    
    private final ParserState<T> startState;
    
    private final T endOfFileSymbol;
    
    private final Deque<Symbol> symbolMatchStack;
    
    private final Deque<ParserState<T>> stateStack;
    
    private final Deque<DataStackElement> dataStack;
    
    public Parser(final Set<ParserState<T>> parserStates, final Set<Edge<T>> edges, final ParserState<T> startState,
            final T endOfFileSymbol) {
        super();
        if (parserStates == null || edges == null || startState == null || endOfFileSymbol == null) {
        	throw new IllegalArgumentException();
        }
        this.parserStates = parserStates;
        this.edges = edges;
        this.startState = startState;
        this.endOfFileSymbol = endOfFileSymbol;
        this.symbolMatchStack = new ArrayDeque<>();
        this.stateStack = new ArrayDeque<>();
        this.dataStack = new ArrayDeque<>();
        
        this.calculateParseTable();
    }
    
    public void calculateParseTable() {
        for (final Edge<T> edge : edges) {
            final ParserState<T> parserState = edge.getInitialState();
            final Symbol symbol = edge.getSymbol();
            final ParserState<T> destinationState = edge.getFinalState();
            final Action action;
            if (symbol instanceof TerminalSymbol) {
                action = new Shift<>(this, destinationState);
            } else if (symbol instanceof NonTerminalSymbol) {
                action = new Goto<>(this, destinationState);
            } else {
                throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
            }
            parserState.putAction(symbol, action);
        }
        for (final ParserState<T> parserState : parserStates) {
            for (final LookaheadItem<T> item : parserState.getItems()) {
                if (item.isComplete()) {
                    final Action action = new Reduce<>(this, item);
                    for (final T lookahead : item.getLookaheadSet()) {
                        parserState.putAction(lookahead, action);
                    }
                } else {
                    final Symbol symbol = item.getNextSymbol();
                    if (symbol.equals(endOfFileSymbol)) {
                        parserState.putAction(symbol, new Accept<>(this));
                    }
                }
            }
        }
//        parsingTable.printTableLong();
    }
    
    private Token<T> currentToken;
    
    private Token<T> lookaheadToken;

    private boolean done;

    private ParserState<T> currentState;

    private Token<T> nextToken;

    private Iterator<Token<T>> iterator;
    
    protected Token<T> getNextToken(final Iterator<Token<T>> iterator) {
        if (currentToken == null) {
            currentToken = iterator.next();
        } else {
            currentToken = lookaheadToken;
        }
        if (iterator.hasNext()) {
            lookaheadToken = iterator.next();
        } else {
            lookaheadToken = new Token<T>() {
                
                @Override
                public T getSymbol() {
                    return endOfFileSymbol;
                }
                
            };
        }
        return currentToken;
    }
    
    /**
     * Parses a sequence of terminal symbols and returns an abstract syntax tree.  This runs in {@code O(n)} time.
     * 
     * @param lexer the lexer that generates an input sequence of terminal symbols
     * @return an abstract syntax tree as constructed by the various {@link ProductionHandler} implementations used in
     *         the {@link Grammar}
     */
    public Object parse(final Lexer<T> lexer) {
        symbolMatchStack.clear();
        stateStack.clear();
        dataStack.clear();
    	currentToken = null;
    	lookaheadToken = null;
        currentState = startState;
        stateStack.push(currentState);
        iterator = lexer.iterator();
        nextToken = getNextToken(iterator);
        done = false;
        do {
            final T symbol = nextToken.getSymbol();
            final Action action = currentState.getAction(symbol);
            action.perform();
        } while ( !done);
        symbolMatchStack.pop();
        stateStack.pop();
        return dataStack.pop().getAbstractSyntaxTreeElement();
    }

    void accept() {
        done = true;
//        System.out.println("Accept.");
    }

    void goTo(final ParserState<T> destinationState) {
        currentState = destinationState;
    }

    void shift(final ParserState<T> destinationState) {
        currentState = destinationState;
        symbolMatchStack.push(nextToken.getSymbol());
        stateStack.push(currentState);
        dataStack.push(new DataStackElement(nextToken));
        nextToken = getNextToken(iterator);
    }

    void reduce(final LookaheadItem<T> reduceItem) {
        final NonTerminalSymbol targetNonTerminal = reduceItem.getTarget();
        final Production production = reduceItem.getProduction();
        final List<DataStackElement> data = new LinkedList<>();
        for (@SuppressWarnings("unused") final Symbol symbol : production.getSymbols()) {
            symbolMatchStack.pop();
            stateStack.pop();
            final DataStackElement datum = dataStack.pop();
            data.add(0, datum);
        }
        final ProductionHandler handler = production.getProductionHandler();
        final DataStackElement newDatum = new DataStackElement(handler.handleReduction(data));
        currentState = stateStack.peek();
        final Action gotoAction = currentState.getAction(targetNonTerminal);
        assert gotoAction instanceof Goto;
        gotoAction.perform();
        symbolMatchStack.push(targetNonTerminal);
        stateStack.push(currentState);
        dataStack.push(newDatum);
    }
    
}
