package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Period extends UriTerminal {

    private static final Period instance = new Period();

    public static Period getInstance() {
        return instance;
    }

    private Period() {
        super('.');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Period;
    }

}
