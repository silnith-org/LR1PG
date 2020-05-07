package org.silnith.grammar;

/**
 * The parser replaces some symbols on the top of the stack with a new symbol.
 */
class Reduce<T extends TerminalSymbol> implements Action {
    
    private final Parser<T> parser;
    
    private final LookaheadItem<T> reduceItem;
    
    public Reduce(final Parser<T> parser, final LookaheadItem<T> reduceItem) {
        super();
        if (reduceItem == null) {
            throw new IllegalArgumentException();
        }
        this.parser = parser;
        this.reduceItem = reduceItem;
    }
    
    @Override
    public boolean perform() {
        return parser.reduce(reduceItem);
    }

    @Override
    public String toString() {
        return "Reduce(" + reduceItem + ")";
    }
    
}