package org.silnith.grammar;

/**
 * The parser accepts the input as a complete "statement" in the language.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Accept<T extends TerminalSymbol> extends AbstractAction<T> {
    
    /**
     * Creates a new "accept" action.
     * 
     * @param parser the parser to act upon
     */
    public Accept(final Parser<T> parser) {
        super(parser);
    }
    
    @Override
    public boolean perform(final ParserData<T> data) {
        Parser<T> r = getParser();
        data.setDone();
        return false;
    }

    @Override
    public String toString() {
        return "Accept";
    }
    
}