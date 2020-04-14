package org.silnith.grammar.uri;

public class Asterisk extends UriTerminal {

    private static final Asterisk instance = new Asterisk();

    public static Asterisk getInstance() {
        return instance;
    }

    private Asterisk() {
        super('*');
    }

}
