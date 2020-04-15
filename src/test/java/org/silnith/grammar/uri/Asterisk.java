package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Asterisk extends UriTerminal {

    private static final Asterisk instance = new Asterisk();

    public static Asterisk getInstance() {
        return instance;
    }

    private Asterisk() {
        super('*');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Asterisk;
    }

}
