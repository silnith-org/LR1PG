package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Equals extends UriTerminal {

    private static final Equals instance = new Equals();

    public static Equals getInstance() {
        return instance;
    }

    private Equals() {
        super('=');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Equals;
    }

}
