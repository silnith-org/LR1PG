package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class ExclamationMark extends UriTerminal {

    private static final ExclamationMark instance = new ExclamationMark();

    public static ExclamationMark getInstance() {
        return instance;
    }

    private ExclamationMark() {
        super('!');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.ExclamationMark;
    }

}
