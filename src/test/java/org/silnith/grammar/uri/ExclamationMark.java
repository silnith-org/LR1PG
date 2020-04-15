package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class ExclamationMark extends UriTerminal {

    private static final ExclamationMark instance = new ExclamationMark();

    public static ExclamationMark getInstance() {
        return instance;
    }

    private ExclamationMark() {
        super('!');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.ExclamationMark;
    }

}
