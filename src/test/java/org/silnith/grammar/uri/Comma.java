package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Comma extends UriTerminal {

    private static final Comma instance = new Comma();

    public static Comma getInstance() {
        return instance;
    }

    private Comma() {
        super(',');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Comma;
    }

}
