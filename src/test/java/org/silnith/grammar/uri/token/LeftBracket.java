package org.silnith.grammar.uri.token;

public class LeftBracket extends UriTerminal {

    private static final LeftBracket instance = new LeftBracket();

    public static LeftBracket getInstance() {
        return instance;
    }

    private LeftBracket() {
        super('[');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.LeftBracket;
    }

}
