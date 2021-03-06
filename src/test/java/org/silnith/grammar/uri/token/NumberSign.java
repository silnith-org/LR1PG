package org.silnith.grammar.uri.token;

public class NumberSign extends UriTerminal {

    private static final NumberSign instance = new NumberSign();

    public static NumberSign getInstance() {
        return instance;
    }

    private NumberSign() {
        super('#');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.NumberSign;
    }

}
