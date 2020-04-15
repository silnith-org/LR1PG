package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbol;

public class QuestionMark extends UriTerminal {

    private static final QuestionMark instance = new QuestionMark();

    public static QuestionMark getInstance() {
        return instance;
    }

    private QuestionMark() {
        super('?');
    }

    @Override
    public TerminalSymbol getSymbol() {
        return UriTerminalType.QuestionMark;
    }

}
