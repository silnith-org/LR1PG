package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class RightParenthesis extends UriTerminal {

    private static final RightParenthesis instance = new RightParenthesis();

    public static RightParenthesis getInstance() {
        return instance;
    }

    private RightParenthesis() {
        super(')');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.RightParenthesis;
    }

}
