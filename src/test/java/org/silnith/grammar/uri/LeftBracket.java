package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class LeftBracket extends UriTerminal {

    private static final LeftBracket instance = new LeftBracket();

    public static LeftBracket getInstance() {
        return instance;
    }

    private LeftBracket() {
        super('[');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.LeftBracket;
    }

}
