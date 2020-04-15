package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class RightBracket extends UriTerminal {

    private static final RightBracket instance = new RightBracket();

    public static RightBracket getInstance() {
        return instance;
    }

    private RightBracket() {
        super(']');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.RightBracket;
    }

}
