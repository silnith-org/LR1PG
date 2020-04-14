package org.silnith.grammar.uri;

public class RightParenthesis extends UriTerminal {

    private static final RightParenthesis instance = new RightParenthesis();

    public static RightParenthesis getInstance() {
        return instance;
    }

    private RightParenthesis() {
        super(')');
    }

}
