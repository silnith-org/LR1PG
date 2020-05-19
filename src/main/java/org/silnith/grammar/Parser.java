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
                action = new Shift<>(destinationState);
            } else if (symbol instanceof NonTerminalSymbol) {
                action = new Goto<>(destinationState);
            } else {
                throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
            }
            parserState.putAction(symbol, action);
        }
        for (final ParserState<T> parserState : parserStates) {
            for (final LookaheadItem<T> lookaheadItem : parserState.getItems()) {
                final Item item = lookaheadItem.getItem();
                if (item.isComplete()) {
                    final Action<T> action = new Reduce<>(lookaheadItem);
                    for (final T lookahead : lookaheadItem.getLookaheadSet()) {
                        parserState.putAction(lookahead, action);
                    }
                } else {
                    final Symbol symbol = item.getNextSymbol();
                    if (endOfFileSymbol.equals(symbol)) {
                        final Action<T> action = new Accept<>();
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
        final ParserData<T> parserData = new ParserData<>();
        
        final TempLexer<T> lexer = new TempLexer<>(inputLexer.iterator(), finalToken);
        parserData.setState(startState);
        
        do {
            parserData.pushState();
            final Token<T> token = lexer.getToken();
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
    
}
