package org.silnith.grammar.uri.token;

public class RightParenthesis extends UriTerminal {

    private static final RightParenthesis instance = new RightParenthesis();

    public static RightParenthesis getInstance() {
        return instance;
    }

    private RightParenthesis() {
        super(')');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.RightParenthesis;
    }

}
