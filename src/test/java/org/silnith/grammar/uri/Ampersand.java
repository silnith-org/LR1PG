package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Ampersand extends UriTerminal {

    private static final Ampersand instance = new Ampersand();

    public static Ampersand getInstance() {
        return instance;
    }

    private Ampersand() {
        super('&');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Ampersand;
    }

}
