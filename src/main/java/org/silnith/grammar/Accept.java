package org.silnith.grammar;

/**
 * The parser accepts the input as a complete "statement" in the language.
 */
class Accept<T extends TerminalSymbol> implements Action {
    
    private final Parser<T> parser;

    /**
     * Creates a new "accept" action.
     * 
     * @param parser the parser to act upon
     */
    public Accept(final Parser<T> parser) {
        super();
        this.parser = parser;
    }
    
    @Override
    public boolean perform() {
        return parser.accept();
    }

    @Override
    public String toString() {
        return "Accept";
    }
    
}