package org.silnith.grammar.uri;

public class Semicolon extends UriTerminal {

    private static final Semicolon instance = new Semicolon();

    public static Semicolon getInstance() {
        return instance;
    }

    private Semicolon() {
        super(';');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Semicolon;
    }

}
