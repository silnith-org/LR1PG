package org.silnith.grammar.uri;

import java.util.HashMap;
import java.util.Map;

import org.silnith.grammar.TerminalSymbolMatch;

public class Digit extends UriTerminal {

    private static final Map<Character, Digit> instances;

    static {
        instances = new HashMap<Character, Digit>(10);
        for (char digit = '0'; digit <= '9'; digit++) {
            instances.put(digit, new Digit(digit));
        }
    }

    public static Digit getInstance(final char character) {
        return instances.get(character);
    }

    private Digit(final char digit) {
        super(digit);
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Digit;
    }

}
