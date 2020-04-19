package org.silnith.grammar.uri.token;

public class Colon extends UriTerminal {

    private static final Colon instance = new Colon();

    public static Colon getInstance() {
        return instance;
    }

    private Colon() {
        super(':');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Colon;
    }

}
