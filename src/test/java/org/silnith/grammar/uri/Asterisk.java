package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Asterisk extends UriTerminal {

    private static final Asterisk instance = new Asterisk();

    public static Asterisk getInstance() {
        return instance;
    }

    private Asterisk() {
        super('*');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Asterisk;
    }

}
