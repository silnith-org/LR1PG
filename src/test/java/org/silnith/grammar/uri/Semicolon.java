package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Semicolon extends UriTerminal {

    private static final Semicolon instance = new Semicolon();

    public static Semicolon getInstance() {
        return instance;
    }

    private Semicolon() {
        super(';');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Semicolon;
    }

}
