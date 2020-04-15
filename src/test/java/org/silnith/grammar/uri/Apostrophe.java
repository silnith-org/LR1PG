package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Apostrophe extends UriTerminal {

    private static final Apostrophe instance = new Apostrophe();

    public static Apostrophe getInstance() {
        return instance;
    }

    private Apostrophe() {
        super('\'');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Apostrophe;
    }

}
