package org.silnith.grammar.uri.token;

public class Apostrophe extends UriTerminal {

    private static final Apostrophe instance = new Apostrophe();

    public static Apostrophe getInstance() {
        return instance;
    }

    private Apostrophe() {
        super('\'');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Apostrophe;
    }

}
