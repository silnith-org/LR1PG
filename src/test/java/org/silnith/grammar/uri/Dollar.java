package org.silnith.grammar.uri;

public class Dollar extends UriTerminal {

    private static final Dollar instance = new Dollar();

    public static Dollar getInstance() {
        return instance;
    }

    private Dollar() {
        super('$');
    }

}
