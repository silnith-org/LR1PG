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
     * An action to take on consuming a symbol.
     * 
     * @param <T> the type of terminal symbols
     */
    public abstract class AbstractAction implements Action<T> {
        
        private final ItemSet<T> sourceState;
        
        private final Symbol symbol;
        
        public AbstractAction(final ItemSet<T> sourceState, final Symbol symbol) {
            super();
            if (sourceState == null || symbol == null) {
                throw new IllegalArgumentException();
            }
            this.sourceState = sourceState;
            this.symbol = symbol;
        }
        
        public ItemSet<T> getSourceState() {
            return sourceState;
        }
        
        public Symbol getSymbol() {
            return symbol;
        }
        
        public abstract Type getType();
        
        /**
         * Perform the action on the parser.
         */
        public abstract void perform();
        
    }

    /**
     * The parser accepts the input as a complete "statement" in the language.
     */
    public class Accept extends AbstractAction {
        
        /**
         * Creates a new "accept" action.
         * 
         * @param sourceState the source state to accept
         * @param symbol the next symbol to consume.  This should be whatever the end-of-file symbol is.
         */
        public Accept(final ItemSet<T> sourceState, final Symbol symbol) {
            super(sourceState, symbol);
        }
        
        @Override
        public Type getType() {
            return Type.ACCEPT;
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
    public class Goto extends AbstractAction {
        
        private final ItemSet<T> destinationState;
        
        public Goto(final ItemSet<T> sourceState, final Symbol symbol, final ItemSet<T> destinationState) {
            super(sourceState, symbol);
            if (destinationState == null) {
                throw new IllegalArgumentException();
            }
            this.destinationState = destinationState;
        }
        
        @Override
        public Type getType() {
            return Type.GOTO;
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
    public class Shift extends AbstractAction {
        
        private final ItemSet<T> destinationState;
        
        public Shift(final ItemSet<T> sourceState, final Symbol symbol, final ItemSet<T> destinationState) {
            super(sourceState, symbol);
            if (destinationState == null) {
                throw new IllegalArgumentException();
            }
            this.destinationState = destinationState;
        }
        
        @Override
        public Type getType() {
            return Type.SHIFT;
        }
        
        @Override
        public String toString() {
            return "Shift(" + destinationState + ")";
        }

        @Override
        public void perform() {
            currentState = destinationState;
            symbolMatchStack.push(nextSymbol.getSymbol());
            stateStack.push(currentState);
            dataStack.push(new DataStackElement(nextSymbol));
            nextSymbol = getNextSymbol(iterator);
        }
        
    }

    /**
     * The parser replaces some symbols on the top of the stack with a new symbol.
     */
    public class Reduce extends AbstractAction {
        
        private final LookaheadItem<T> reduceItem;
        
        public Reduce(final ItemSet<T> sourceState, final Symbol symbol, final LookaheadItem<T> reduceItem) {
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
        
        @Override
        public String toString() {
            return "Reduce(" + reduceItem + ")";
        }

        @Override
        public void perform() {
            final NonTerminalSymbol leftHandSide = reduceItem.getLeftHandSide();
            final Production production = reduceItem.getRightHandSide();
            final List<DataStackElement> data = new LinkedList<>();
            for (@SuppressWarnings("unused")
            final Symbol symbol : production.getSymbols()) {
                symbolMatchStack.pop();
                stateStack.pop();
                final DataStackElement datum = dataStack.pop();
                data.add(0, datum);
            }
            final ProductionHandler handler = production.getProductionHandler();
            final DataStackElement newDatum = new DataStackElement(handler.handleReduction(data));
            currentState = stateStack.peek();
            final Action<T> tempAction = getAction(currentState, leftHandSide);
            assert tempAction instanceof Parser.Goto;
            final Goto gotoAction = (Goto) tempAction;
            gotoAction.perform();
            symbolMatchStack.push(leftHandSide);
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
            final Action<T> action;
            if (symbol instanceof TerminalSymbol) {
                final Shift shiftAction = new Shift(parserState, symbol, destinationState);
                action = shiftAction;
            } else if (symbol instanceof NonTerminalSymbol) {
                final Goto gotoAction = new Goto(parserState, symbol, destinationState);
                action = gotoAction;
            } else {
                throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
            }
            putAction(parserState, symbol, action);
        }
        for (final ItemSet<T> parserState : parserStates) {
            for (final LookaheadItem<T> item : parserState.getItems()) {
                if (item.isComplete()) {
                    for (final T lookahead : item.getLookaheadSet()) {
                        putAction(parserState, lookahead, new Reduce(parserState, lookahead, item));
                    }
                } else {
                    final Symbol symbol = item.getNextSymbol();
                    if (symbol.equals(endOfFileSymbol)) {
                        putAction(parserState, symbol, new Accept(parserState, symbol));
                    }
                }
            }
        }
//        parsingTable.printTableLong();
    }
    
    /**
     * Adds an action to the parse table.
     * 
     * @param parserState the current parser state
     * @param symbol the next symbol to be consumed
     * @param action the parser action to take
     */
    protected void putAction(final ItemSet<T> parserState, final Symbol symbol, final Action<T> action) {
        parserState.putAction(symbol, action);
    }
    
    private Action<T> getAction(final ItemSet<T> currentState, final Symbol symbol) {
        return currentState.getAction(symbol);
	}

    private Token<T> currentSymbol;
    
    private Token<T> lookahead;

    private boolean done;

    private ItemSet<T> currentState;

    private Token<T> nextSymbol;

    private Iterator<Token<T>> iterator;
    
    protected Token<T> getNextSymbol(final Iterator<Token<T>> iterator) {
        if (currentSymbol == null) {
            currentSymbol = iterator.next();
        } else {
            currentSymbol = lookahead;
        }
        if (iterator.hasNext()) {
            lookahead = iterator.next();
        } else {
            lookahead = new Token<T>() {
                
                @Override
                public T getSymbol() {
                    return endOfFileSymbol;
                }
                
            };
        }
        return currentSymbol;
    }
    
    protected Token<T> getLookahead() {
        return lookahead;
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
    	currentSymbol = null;
    	lookahead = null;
        currentState = startState;
        stateStack.push(currentState);
        iterator = lexer.iterator();
        nextSymbol = getNextSymbol(iterator);
        done = false;
        do {
            final Action<T> action = getAction(currentState, nextSymbol.getSymbol());
            action.perform();
        } while ( !done);
        symbolMatchStack.pop();
        stateStack.pop();
        return dataStack.pop().getAbstractSyntaxTreeElement();
    }
    
}
