package org.silnith.grammar.uri;

public class Ampersand extends UriTerminal {

    private static final Ampersand instance = new Ampersand();

    public static Ampersand getInstance() {
        return instance;
    }

    private Ampersand() {
        super('&');
    }

}
