package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class ForwardSlash extends UriTerminal {

    private static final ForwardSlash instance = new ForwardSlash();

    public static ForwardSlash getInstance() {
        return instance;
    }

    private ForwardSlash() {
        super('/');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.ForwardSlash;
    }

}
