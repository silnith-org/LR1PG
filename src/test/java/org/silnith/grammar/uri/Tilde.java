package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Tilde extends UriTerminal {

    private static final Tilde instance = new Tilde();

    public static Tilde getInstance() {
        return instance;
    }

    private Tilde() {
        super('~');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Tilde;
    }

}