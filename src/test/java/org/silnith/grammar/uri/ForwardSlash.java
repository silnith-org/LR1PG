package org.silnith.grammar.uri;

public class ForwardSlash extends UriTerminal {

    private static final ForwardSlash instance = new ForwardSlash();

    public static ForwardSlash getInstance() {
        return instance;
    }

    private ForwardSlash() {
        super('/');
    }

}
