package org.silnith.grammar;

/**
 * The parser replaces some symbols on the top of the stack with a new symbol.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Reduce<T extends TerminalSymbol> extends AbstractAction<T> {
    
    private final LookaheadItem<T> reduceItem;
    
    public Reduce(final Parser<T> parser, final LookaheadItem<T> reduceItem) {
        super(parser);
        if (reduceItem == null) {
            throw new IllegalArgumentException();
        }
        this.reduceItem = reduceItem;
    }
    
    @Override
    public void perform() {
        getParser().reduce(reduceItem);
    }

    @Override
    public String toString() {
        return "Reduce(" + reduceItem + ")";
    }
    
}