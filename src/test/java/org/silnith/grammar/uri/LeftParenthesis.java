package org.silnith.grammar.uri;

public class LeftParenthesis extends UriTerminal {

    private static final LeftParenthesis instance = new LeftParenthesis();

    public static LeftParenthesis getInstance() {
        return instance;
    }

    private LeftParenthesis() {
        super('(');
    }

}
