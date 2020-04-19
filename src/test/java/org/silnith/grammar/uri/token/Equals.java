package org.silnith.grammar.uri.token;

public class Equals extends UriTerminal {

    private static final Equals instance = new Equals();

    public static Equals getInstance() {
        return instance;
    }

    private Equals() {
        super('=');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Equals;
    }

}
