package org.silnith.grammar.uri.token;

public class Asterisk extends UriTerminal {

    private static final Asterisk instance = new Asterisk();

    public static Asterisk getInstance() {
        return instance;
    }

    private Asterisk() {
        super('*');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Asterisk;
    }

}
