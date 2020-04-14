package org.silnith.grammar.uri;

public class ExclamationMark extends UriTerminal {

    private static final ExclamationMark instance = new ExclamationMark();

    public static ExclamationMark getInstance() {
        return instance;
    }

    private ExclamationMark() {
        super('!');
    }

}
