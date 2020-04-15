package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class AtSign extends UriTerminal {

    private static final AtSign instance = new AtSign();

    public static AtSign getInstance() {
        return instance;
    }

    private AtSign() {
        super('@');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.AtSign;
    }

}
