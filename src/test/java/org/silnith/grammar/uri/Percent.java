package org.silnith.grammar.uri;

public class Percent extends UriTerminal {

    private static final Percent instance = new Percent();

    public static Percent getInstance() {
        return instance;
    }

    private Percent() {
        super('%');
    }

}