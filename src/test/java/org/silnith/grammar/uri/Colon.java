package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Colon extends UriTerminal {

    private static final Colon instance = new Colon();

    public static Colon getInstance() {
        return instance;
    }

    private Colon() {
        super(':');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Colon;
    }

}
