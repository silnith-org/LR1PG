package org.silnith.grammar.uri.token;

public class Underscore extends UriTerminal {

    private static final Underscore instance = new Underscore();

    public static Underscore getInstance() {
        return instance;
    }

    private Underscore() {
        super('_');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Underscore;
    }

}