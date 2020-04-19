package org.silnith.grammar.uri.token;

public class Percent extends UriTerminal {

    private static final Percent instance = new Percent();

    public static Percent getInstance() {
        return instance;
    }

    private Percent() {
        super('%');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Percent;
    }

}