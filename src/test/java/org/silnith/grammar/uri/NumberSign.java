package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class NumberSign extends UriTerminal {

    private static final NumberSign instance = new NumberSign();

    public static NumberSign getInstance() {
        return instance;
    }

    private NumberSign() {
        super('#');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.NumberSign;
    }

}
