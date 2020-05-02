package org.silnith.grammar;

import java.util.Set;

public class ItemSetFactory<T extends TerminalSymbol> extends WeakCanonicalFactory<ItemSet<T>> {
    
    public ItemSet<T> createInstance(final Set<LookaheadItem<T>> items) {
        return valueOf(new ItemSet<>(items));
    }
    
}
