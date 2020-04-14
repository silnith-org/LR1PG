package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestLexer implements Lexer<CharacterTerminal> {

    public static final CharacterTerminal endOfFileSymbol = new CharacterTerminal((char) 0);

    private final String input;

    public TestLexer(final String input) {
        super();
        this.input = input;
    }

    @Override
    public Iterator<CharacterTerminal> iterator() {
        final List<CharacterTerminal> foo = new ArrayList<CharacterTerminal>();
        for (final char c : input.toCharArray()) {
            foo.add(new CharacterTerminal(c));
        }
        foo.add(TestLexer.endOfFileSymbol);
        return foo.iterator();
    }

}
