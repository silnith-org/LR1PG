package org.silnith.grammar.uri.token;

public class Dollar extends UriTerminal {

    private static final Dollar instance = new Dollar();

    public static Dollar getInstance() {
        return instance;
    }

    private Dollar() {
        super('$');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.Dollar;
    }

}
