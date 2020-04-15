package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaticLexer implements Lexer {
    
    public static class TerminalWrapper implements Terminal {

        private final TerminalSymbolMatch tsm;

        public TerminalWrapper(TerminalSymbolMatch tsm) {
            this.tsm = tsm;
        }

        @Override
        public TerminalSymbolMatch getMatch() {
            return tsm;
        }

        @Override
        public String toString() {
            return String.valueOf(tsm);
        }

    }

    private final List<Terminal> iterable;

    public StaticLexer(final TerminalSymbolMatch... iterable) {
        super();
        this.iterable = new ArrayList<Terminal>();
        for (final TerminalSymbolMatch tsm : iterable) {
            this.iterable.add(new TerminalWrapper(tsm));
        }
    }

    @Override
    public Iterator<Terminal> iterator() {
        return iterable.iterator();
    }
    
}