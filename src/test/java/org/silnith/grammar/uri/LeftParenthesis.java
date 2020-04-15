package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class LeftParenthesis extends UriTerminal {

    private static final LeftParenthesis instance = new LeftParenthesis();

    public static LeftParenthesis getInstance() {
        return instance;
    }

    private LeftParenthesis() {
        super('(');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.LeftParenthesis;
    }

}
