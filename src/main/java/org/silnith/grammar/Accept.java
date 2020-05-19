package org.silnith.grammar;

/**
 * The parser accepts the input as a complete "statement" in the language.
 * 
 * @param <T> the concrete type of terminal symbols
 */
class Accept<T extends TerminalSymbol> implements Action<T> {
    
    /**
     * Creates a new "accept" action.
     */
    public Accept() {
        super();
    }
    
    @Override
    public ParserData<T> perform(final ParserData<T> data) {
        data.setDone();
        return data;
    }

    @Override
    public String toString() {
        return "Accept";
    }
    
}