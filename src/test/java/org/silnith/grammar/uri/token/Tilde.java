package org.silnith.grammar.uri.token;

public class Tilde extends UriTerminal {

    private static final Tilde instance = new Tilde();

    public static Tilde getInstance() {
        return instance;
    }

    private Tilde() {
        super('~');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Tilde;
    }

}