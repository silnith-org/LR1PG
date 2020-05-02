package org.silnith.grammar;

import java.util.Set;

public class LookaheadItemFactory<T extends TerminalSymbol> extends WeakCanonicalFactory<LookaheadItem<T>> {
    
    public LookaheadItemFactory() {
        super();
    }
    
    public LookaheadItem<T> createInstance(final Item item, final Set<T> lookaheadSet) {
        return valueOf(new LookaheadItem<>(item, lookaheadSet));
    }
    
}
