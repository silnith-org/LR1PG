package org.silnith.grammar.uri;

public class Hyphen extends UriTerminal {

    private static final Hyphen instance = new Hyphen();

    public static Hyphen getInstance() {
        return instance;
    }

    private Hyphen() {
        super('-');
    }

}