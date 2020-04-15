package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Equals extends UriTerminal {

    private static final Equals instance = new Equals();

    public static Equals getInstance() {
        return instance;
    }

    private Equals() {
        super('=');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Equals;
    }

}
