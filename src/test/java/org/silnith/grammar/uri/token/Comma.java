package org.silnith.grammar.uri.token;

public class Comma extends UriTerminal {

    private static final Comma instance = new Comma();

    public static Comma getInstance() {
        return instance;
    }

    private Comma() {
        super(',');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Comma;
    }

}
