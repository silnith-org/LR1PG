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

    /**
     * The parser accepts the input as a complete "statement" in the language.
     */
    private class Accept implements Action {
        
        /**
         * Creates a new "accept" action.
         */
        public Accept() {
            super();
        }
        
        @Override
        public String toString() {
            return "Accept";
        }

        @Override
        public void perform() {
            done = true;
//            System.out.println("Accept.");
        }
        
    }

    /**
     * The parser changes state without otherwise modifying the stack.
     */
    private class Goto implements Action {
        
        private final ItemSet<T> destinationState;
        
        public Goto(final ItemSet<T> destinationState) {
            super();
            if (destinationState == null) {
                throw new IllegalArgumentException();
            }
            this.destinationState = destinationState;
        }
        
        @Override
        public String toString() {
            return "Goto(" + destinationState + ")";
        }

        @Override
        public void perform() {
            currentState = destinationState;
        }
        
    }

    /**
     * The parser consumes an additional terminal symbol.
     */
    private class Shift implements Action {
        
        private final ItemSet<T> destinationState;
        
        public Shift(final ItemSet<T> destinationState) {
            super();
            if (destinationState == null) {
                throw new IllegalArgumentException();
            }
            this.destinationState = destinationState;
        }
        
        @Override
        public String toString() {
            return "Shift(" + destinationState + ")";
        }

        @Override
        public void perform() {
            currentState = destinationState;
            symbolMatchStack.push(nextToken.getSymbol());
            stateStack.push(currentState);
            dataStack.push(new DataStackElement(nextToken));
            nextToken = getNextToken(iterator);
        }
        
    }

    /**
     * The parser replaces some symbols on the top of the stack with a new symbol.
     */
    private class Reduce implements Action {
        
        private final LookaheadItem<T> reduceItem;
        
        public Reduce(final LookaheadItem<T> reduceItem) {
            super();
            if (reduceItem == null) {
                throw new IllegalArgumentException();
            }
            this.reduceItem = reduceItem;
        }
        
        @Override
        public String toString() {
            return "Reduce(" + reduceItem + ")";
        }

        @Override
        public void perform() {
            final NonTerminalSymbol targetNonTerminal = reduceItem.getLeftHandSide();
            final Production production = reduceItem.getRightHandSide();
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
            assert gotoAction instanceof Parser.Goto;
            gotoAction.perform();
            symbolMatchStack.push(targetNonTerminal);
            stateStack.push(currentState);
            dataStack.push(newDatum);
        }
        
    }

	private final Set<ItemSet<T>> parserStates;
	
    private final Set<Edge<T>> edges;
    
    private final ItemSet<T> startState;
    
    private final T endOfFileSymbol;
    
    private final Deque<Symbol> symbolMatchStack;
    
    private final Deque<ItemSet<T>> stateStack;
    
    private final Deque<DataStackElement> dataStack;
    
    public Parser(final Set<ItemSet<T>> parserStates, final Set<Edge<T>> edges, final ItemSet<T> startState,
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
            final ItemSet<T> parserState = edge.getInitialState();
            final Symbol symbol = edge.getSymbol();
            final ItemSet<T> destinationState = edge.getFinalState();
            final Action action;
            if (symbol instanceof TerminalSymbol) {
                action = new Shift(destinationState);
            } else if (symbol instanceof NonTerminalSymbol) {
                action = new Goto(destinationState);
            } else {
                throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
            }
            parserState.putAction(symbol, action);
        }
        for (final ItemSet<T> parserState : parserStates) {
            for (final LookaheadItem<T> item : parserState.getItems()) {
                if (item.isComplete()) {
                    final Reduce action = new Reduce(item);
                    for (final T lookahead : item.getLookaheadSet()) {
                        parserState.putAction(lookahead, action);
                    }
                } else {
                    final Symbol symbol = item.getNextSymbol();
                    if (symbol.equals(endOfFileSymbol)) {
                        parserState.putAction(symbol, new Accept());
                    }
                }
            }
        }
//        parsingTable.printTableLong();
    }
    
    private Token<T> currentToken;
    
    private Token<T> lookaheadToken;

    private boolean done;

    private ItemSet<T> currentState;

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
    
}
