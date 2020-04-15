package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class Dollar extends UriTerminal {

    private static final Dollar instance = new Dollar();

    public static Dollar getInstance() {
        return instance;
    }

    private Dollar() {
        super('$');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.Dollar;
    }

}
