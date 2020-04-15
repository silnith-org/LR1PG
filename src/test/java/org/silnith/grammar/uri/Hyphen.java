package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Hyphen extends UriTerminal {

    private static final Hyphen instance = new Hyphen();

    public static Hyphen getInstance() {
        return instance;
    }

    private Hyphen() {
        super('-');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Hyphen;
    }

}