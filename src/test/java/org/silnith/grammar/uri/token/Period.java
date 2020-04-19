package org.silnith.grammar.uri.token;

public class Period extends UriTerminal {

    private static final Period instance = new Period();

    public static Period getInstance() {
        return instance;
    }

    private Period() {
        super('.');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Period;
    }

}
