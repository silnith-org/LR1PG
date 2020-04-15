package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Apostrophe extends UriTerminal {

    private static final Apostrophe instance = new Apostrophe();

    public static Apostrophe getInstance() {
        return instance;
    }

    private Apostrophe() {
        super('\'');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Apostrophe;
    }

}
