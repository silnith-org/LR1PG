package org.silnith.grammar.uri;

public class Period extends UriTerminal {

    private static final Period instance = new Period();

    public static Period getInstance() {
        return instance;
    }

    private Period() {
        super('.');
    }

}