package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Underscore extends UriTerminal {

    private static final Underscore instance = new Underscore();

    public static Underscore getInstance() {
        return instance;
    }

    private Underscore() {
        super('_');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Underscore;
    }

}