package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class AtSign extends UriTerminal {

    private static final AtSign instance = new AtSign();

    public static AtSign getInstance() {
        return instance;
    }

    private AtSign() {
        super('@');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.AtSign;
    }

}
