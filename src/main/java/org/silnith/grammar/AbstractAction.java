package org.silnith.grammar;

abstract class AbstractAction<T extends TerminalSymbol> implements Action<T> {

    private final Parser<T> parser;

    public AbstractAction(final Parser<T> parser) {
        super();
        if (parser == null) {
            throw new IllegalArgumentException();
        }
        this.parser = parser;
    }

    public Parser<T> getParser() {
        return parser;
    }

}
