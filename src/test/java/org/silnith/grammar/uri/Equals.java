package org.silnith.grammar.uri;

public class Equals extends UriTerminal {

    private static final Equals instance = new Equals();

    public static Equals getInstance() {
        return instance;
    }

    private Equals() {
        super('=');
    }

}
