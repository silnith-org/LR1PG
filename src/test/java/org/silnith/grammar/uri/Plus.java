package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Plus extends UriTerminal {

    private static final Plus instance = new Plus();

    public static Plus getInstance() {
        return instance;
    }

    private Plus() {
        super('+');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Plus;
    }

}
