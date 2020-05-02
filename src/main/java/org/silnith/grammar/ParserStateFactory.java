package org.silnith.grammar;

import java.util.Set;

public class ParserStateFactory<T extends TerminalSymbol> extends WeakCanonicalFactory<ParserState<T>> {
    
    public ParserState<T> createInstance(final Set<LookaheadItem<T>> items) {
        return valueOf(new ParserState<>(items));
    }
    
}
