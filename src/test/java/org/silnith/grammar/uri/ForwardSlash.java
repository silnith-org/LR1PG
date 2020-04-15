package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class ForwardSlash extends UriTerminal {

    private static final ForwardSlash instance = new ForwardSlash();

    public static ForwardSlash getInstance() {
        return instance;
    }

    private ForwardSlash() {
        super('/');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.ForwardSlash;
    }

}
