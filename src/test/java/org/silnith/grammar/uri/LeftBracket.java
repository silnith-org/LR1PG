package org.silnith.grammar.uri;

public class LeftBracket extends UriTerminal {

    private static final LeftBracket instance = new LeftBracket();

    public static LeftBracket getInstance() {
        return instance;
    }

    private LeftBracket() {
        super('[');
    }

}
