package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Ampersand extends UriTerminal {

    private static final Ampersand instance = new Ampersand();

    public static Ampersand getInstance() {
        return instance;
    }

    private Ampersand() {
        super('&');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Ampersand;
    }

}
