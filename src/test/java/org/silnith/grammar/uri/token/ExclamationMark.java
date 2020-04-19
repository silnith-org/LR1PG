package org.silnith.grammar.uri.token;

public class ExclamationMark extends UriTerminal {

    private static final ExclamationMark instance = new ExclamationMark();

    public static ExclamationMark getInstance() {
        return instance;
    }

    private ExclamationMark() {
        super('!');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.ExclamationMark;
    }

}
