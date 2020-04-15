package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class RightParenthesis extends UriTerminal {

    private static final RightParenthesis instance = new RightParenthesis();

    public static RightParenthesis getInstance() {
        return instance;
    }

    private RightParenthesis() {
        super(')');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.RightParenthesis;
    }

}
