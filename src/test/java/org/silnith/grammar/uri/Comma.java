package org.silnith.grammar.uri;

public class Comma extends UriTerminal {

    private static final Comma instance = new Comma();

    public static Comma getInstance() {
        return instance;
    }

    private Comma() {
        super(',');
    }

}
