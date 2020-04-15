package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class LeftBracket extends UriTerminal {

    private static final LeftBracket instance = new LeftBracket();

    public static LeftBracket getInstance() {
        return instance;
    }

    private LeftBracket() {
        super('[');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.LeftBracket;
    }

}
