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
    
    private TempLexer<T> lexer;

    private Token<T> token;
    
    private ParserData<T> parserData;
    
    public Parser(final Set<ParserState<T>> parserStates, final Set<Edge<T>> edges, final ParserState<T> startState,
            final T endOfFileSymbol) {
        super();
        if (parserStates == null || edges == null || startState == null || endOfFileSymbol == null) {
        	throw new IllegalArgumentException();
        }
        this.startState = startState;
        this.finalToken = new FinalToken<>(endOfFileSymbol);
        
        for (final Edge<T> edge : edges) {
            final ParserState<T> parserState = edge.getInitialState();
            final Symbol symbol = edge.getSymbol();
            final ParserState<T> destinationState = edge.getFinalState();
            
            final Action<T> action;
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
            for (final LookaheadItem<T> lookaheadItem : parserState.getItems()) {
                final Item item = lookaheadItem.getItem();
                if (item.isComplete()) {
                    final Action<T> action = new Reduce<>(this, lookaheadItem);
                    for (final T lookahead : lookaheadItem.getLookaheadSet()) {
                        parserState.putAction(lookahead, action);
                    }
                } else {
                    final Symbol symbol = item.getNextSymbol();
                    if (endOfFileSymbol.equals(symbol)) {
                        final Action<T> action = new Accept<>(this);
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
        parserData = new ParserData<>();
        
        lexer = new TempLexer<>(inputLexer.iterator(), finalToken);
        parserData.setState(startState);
        
        do {
            parserData.pushState();
            token = lexer.getToken();
            final T lookaheadSymbol = token.getSymbol();
            boolean readyForShift;
            do {
                final Set<Action<T>> actions = parserData.getActions(lookaheadSymbol);
                assert actions != null;
                
                final Action<T> action = parserData.getAction(lookaheadSymbol);
                readyForShift = action.perform(parserData);
            } while ( !parserData.isDone() && !readyForShift);
            if (readyForShift) {
                parserData.pushData(token);
            }
        } while ( !parserData.isDone());
        parserData.popState();
        final Object data = parserData.popData();
        return data;
    }

    /**
     * Accept the complete language string.
     */
    boolean accept(final ParserData<T> parserData) {
        parserData.setDone();
        return false;
    }

    /**
     * Change to a new parser state.
     * 
     * @param destinationState the next parser state
     */
    boolean goTo(final ParserData<T> parserData, final ParserState<T> destinationState) {
        parserData.setState(destinationState);
        return false;
    }

    /**
     * Read the next token and put it on the stack.
     * 
     * @param destinationState the next parser state
     */
    boolean shift(final ParserData<T> parserData, final ParserState<T> destinationState) {
        parserData.setState(destinationState);
        return true;
    }

    /**
     * Apply a production reduction to the stack.  This removes the symbols for each element of the production,
     * passes them through the production handler, and puts the output of the production handler onto the stack.
     * 
     * @param reduceItem the production to reduce
     */
    boolean reduce(final ParserData<T> parserData, final LookaheadItem<T> reduceItem) {
        final Item item = reduceItem.getItem();
        final NonTerminalSymbol targetNonTerminal = item.getTarget();
        final Production production = item.getProduction();
        final List<Symbol> symbols = production.getSymbols();
        final Deque<Object> data = new ArrayDeque<>(symbols.size());
        for (@SuppressWarnings("unused") final Symbol symbol : symbols) {
            parserData.popState();
            final Object datum = parserData.popData();
            data.addFirst(datum);
        }
        final ProductionHandler handler = production.getProductionHandler();
        final Object newDatum = handler.handleReduction(new ArrayList<>(data));
        parserData.setState(parserData.peekState());
        final Action<T> gotoAction = parserData.getAction(targetNonTerminal);
        assert gotoAction instanceof Goto;
        
        assert parserData.getActions(targetNonTerminal).size() == 1;
        
        gotoAction.perform(parserData);
        parserData.pushState();
        parserData.pushData(newDatum);
        return false;
    }
    
}
