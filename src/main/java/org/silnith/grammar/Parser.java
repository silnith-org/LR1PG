package org.silnith.grammar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
    
    private final ParserState<T> startState;
    
    private final Token<T> finalToken;

    private final Deque<ParserState<T>> stateStack;
    
    private final Deque<DataStackElement> dataStack;
    
    private TempLexer<T> lexer;

    private Token<T> token;

    private ParserState<T> state;

    public Parser(final Set<ParserState<T>> parserStates, final Set<Edge<T>> edges, final ParserState<T> startState,
            final T endOfFileSymbol) {
        super();
        if (parserStates == null || edges == null || startState == null || endOfFileSymbol == null) {
        	throw new IllegalArgumentException();
        }
        this.startState = startState;
        this.finalToken = new FinalToken<>(endOfFileSymbol);
        
        this.stateStack = new ArrayDeque<>();
        this.dataStack = new ArrayDeque<>();
        
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
                    if (endOfFileSymbol.equals(symbol)) {
                        final Action action = new Accept<>(this);
                        parserState.putAction(symbol, action);
                    }
                }
            }
        }
    }
    
    /**
     * Parses a sequence of terminal symbols and returns an abstract syntax tree.  This runs in {@code O(n)} time.
     * 
     * @param inputLexer the lexer that generates an input sequence of terminal symbols
     * @return an abstract syntax tree as constructed by the various {@link ProductionHandler} implementations used in
     *         the {@link Grammar}
     */
    public Object parse(final Lexer<T> inputLexer) {
        stateStack.clear();
        dataStack.clear();
        
        lexer = new TempLexer<>(inputLexer.iterator(), finalToken);
    	state = startState;
        stateStack.push(state);
        token = lexer.getToken();
        
        boolean done;
        do {
            final T symbol = token.getSymbol();
            final Action action = state.getAction(symbol);
            done = action.perform();
        } while ( !done);
        stateStack.pop();
        final DataStackElement datum = dataStack.pop();
        return datum.getAbstractSyntaxTreeElement();
    }

    /**
     * Accept the complete language string.
     */
    boolean accept() {
        return true;
    }

    /**
     * Change to a new parser state.
     * 
     * @param destinationState the next parser state
     */
    boolean goTo(final ParserState<T> destinationState) {
        state = destinationState;
        return false;
    }

    /**
     * Read the next token and put it on the stack.
     * 
     * @param destinationState the next parser state
     */
    boolean shift(final ParserState<T> destinationState) {
        state = destinationState;
        stateStack.push(state);
        dataStack.push(new DataStackElement(token));
        token = lexer.getToken();
        return false;
    }

    /**
     * Apply a production reduction to the stack.  This removes the symbols for each element of the production,
     * passes them through the production handler, and puts the output of the production handler onto the stack.
     * 
     * @param reduceItem the production to reduce
     */
    boolean reduce(final LookaheadItem<T> reduceItem) {
        final NonTerminalSymbol targetNonTerminal = reduceItem.getTarget();
        final Production production = reduceItem.getProduction();
        final List<Symbol> symbols = production.getSymbols();
        final Deque<DataStackElement> data = new ArrayDeque<>(symbols.size());
        for (@SuppressWarnings("unused") final Symbol symbol : symbols) {
            stateStack.pop();
            final DataStackElement datum = dataStack.pop();
            data.addFirst(datum);
        }
        final ProductionHandler handler = production.getProductionHandler();
        final DataStackElement newDatum = new DataStackElement(handler.handleReduction(new ArrayList<>(data)));
        state = stateStack.peek();
        final Action gotoAction = state.getAction(targetNonTerminal);
        assert gotoAction instanceof Goto;
        gotoAction.perform();
        stateStack.push(state);
        dataStack.push(newDatum);
        return false;
    }
    
}
