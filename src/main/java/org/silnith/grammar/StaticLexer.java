package org.silnith.grammar;

import java.util.Iterator;

public class StaticLexer<T extends TerminalSymbol> implements Lexer<T> {
    
    private final Iterable<T> iterable;

    public StaticLexer(final Iterable<T> iterable) {
        super();
        this.iterable = iterable;
    }

    @Override
    public Iterator<T> iterator() {
        return iterable.iterator();
    }
    
}