package org.silnith.grammar.uri;

public class Underscore extends UriTerminal {

    private static final Underscore instance = new Underscore();

    public static Underscore getInstance() {
        return instance;
    }

    private Underscore() {
        super('_');
    }

}