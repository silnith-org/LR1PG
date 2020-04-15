package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Colon extends UriTerminal {

    private static final Colon instance = new Colon();

    public static Colon getInstance() {
        return instance;
    }

    private Colon() {
        super(':');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Colon;
    }

}
