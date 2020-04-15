package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class RightBracket extends UriTerminal {

    private static final RightBracket instance = new RightBracket();

    public static RightBracket getInstance() {
        return instance;
    }

    private RightBracket() {
        super(']');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.RightBracket;
    }

}
