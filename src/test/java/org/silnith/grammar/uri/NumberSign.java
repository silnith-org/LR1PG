package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class NumberSign extends UriTerminal {

    private static final NumberSign instance = new NumberSign();

    public static NumberSign getInstance() {
        return instance;
    }

    private NumberSign() {
        super('#');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.NumberSign;
    }

}
