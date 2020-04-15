package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Period extends UriTerminal {

    private static final Period instance = new Period();

    public static Period getInstance() {
        return instance;
    }

    private Period() {
        super('.');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Period;
    }

}
