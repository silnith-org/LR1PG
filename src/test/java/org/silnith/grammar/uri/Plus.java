package org.silnith.grammar.uri;

public class Plus extends UriTerminal {

    private static final Plus instance = new Plus();

    public static Plus getInstance() {
        return instance;
    }

    private Plus() {
        super('+');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Plus;
    }

}
