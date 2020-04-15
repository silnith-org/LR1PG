package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class LeftParenthesis extends UriTerminal {

    private static final LeftParenthesis instance = new LeftParenthesis();

    public static LeftParenthesis getInstance() {
        return instance;
    }

    private LeftParenthesis() {
        super('(');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.LeftParenthesis;
    }

}
