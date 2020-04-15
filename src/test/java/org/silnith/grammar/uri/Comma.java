package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Comma extends UriTerminal {

    private static final Comma instance = new Comma();

    public static Comma getInstance() {
        return instance;
    }

    private Comma() {
        super(',');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Comma;
    }

}
