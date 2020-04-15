package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple lexer that emits a pre-defined static list of tokens.
 */
public class StaticLexer<T extends TerminalSymbol> implements Lexer<T> {
    
    public static class TerminalWrapper<T extends TerminalSymbol> implements Token<T> {

        private final T tsm;

        public TerminalWrapper(final T tsm) {
            this.tsm = tsm;
        }

        @Override
        public T getSymbol() {
            return tsm;
        }

        @Override
        public String toString() {
            return String.valueOf(tsm);
        }

    }

    private final List<Token<T>> iterable;

    public StaticLexer(final T... iterable) {
        super();
        this.iterable = new ArrayList<>();
        for (final T tsm : iterable) {
            this.iterable.add(new TerminalWrapper<>(tsm));
        }
    }

    @Override
    public Iterator<Token<T>> iterator() {
        return iterable.iterator();
    }
    
}