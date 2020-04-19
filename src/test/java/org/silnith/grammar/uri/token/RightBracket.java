package org.silnith.grammar.uri.token;

public class RightBracket extends UriTerminal {

    private static final RightBracket instance = new RightBracket();

    public static RightBracket getInstance() {
        return instance;
    }

    private RightBracket() {
        super(']');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.RightBracket;
    }

}
