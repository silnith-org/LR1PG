package org.silnith.grammar.uri;

public class AtSign extends UriTerminal {

    private static final AtSign instance = new AtSign();

    public static AtSign getInstance() {
        return instance;
    }

    private AtSign() {
        super('@');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.AtSign;
    }

}
