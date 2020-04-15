package org.silnith.grammar.uri;

public class QuestionMark extends UriTerminal {

    private static final QuestionMark instance = new QuestionMark();

    public static QuestionMark getInstance() {
        return instance;
    }

    private QuestionMark() {
        super('?');
    }

    @Override
    public UriTerminalType getSymbol() {
        return UriTerminalType.QuestionMark;
    }

}
