package org.silnith.grammar.uri;

import java.util.HashMap;
import java.util.Map;

public class AlphaHex extends UriTerminal {

    private static final Map<Character, AlphaHex> instances;

    static {
        instances = new HashMap<Character, AlphaHex>(12);
        for (char c = 'a'; c <= 'f'; c++) {
            final char lowerCase = Character.toLowerCase(c);
            final char upperCase = Character.toUpperCase(c);
            instances.put(lowerCase, new AlphaHex(lowerCase));
            instances.put(upperCase, new AlphaHex(upperCase));
        }
    }

    public static AlphaHex getInstance(final char character) {
        return instances.get(character);
    }

    private AlphaHex(final char character) {
        super(character);
    }

}
