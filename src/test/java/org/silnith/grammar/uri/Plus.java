package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Plus extends UriTerminal {

    private static final Plus instance = new Plus();

    public static Plus getInstance() {
        return instance;
    }

    private Plus() {
        super('+');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Plus;
    }

}
