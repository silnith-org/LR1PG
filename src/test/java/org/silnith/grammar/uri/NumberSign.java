package org.silnith.grammar.uri;

public class NumberSign extends UriTerminal {

    private static final NumberSign instance = new NumberSign();

    public static NumberSign getInstance() {
        return instance;
    }

    private NumberSign() {
        super('#');
    }

}
