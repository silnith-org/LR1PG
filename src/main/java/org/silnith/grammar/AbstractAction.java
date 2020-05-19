package org.silnith.grammar;

abstract class AbstractAction<T extends TerminalSymbol> implements Action<T> {

    public AbstractAction(final Parser<T> parser) {
        super();
        if (parser == null) {
            throw new IllegalArgumentException();
        }
    }

}
