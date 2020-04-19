package org.silnith.grammar.uri.token;

public class Ampersand extends UriTerminal {

    private static final Ampersand instance = new Ampersand();

    public static Ampersand getInstance() {
        return instance;
    }

    private Ampersand() {
        super('&');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Ampersand;
    }

}
