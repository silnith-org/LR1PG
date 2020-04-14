package org.silnith.grammar.uri;

import java.util.HashMap;
import java.util.Map;

public class AlphaNonHex extends UriTerminal {

    private static final Map<Character, AlphaNonHex> instances;

    static {
        instances = new HashMap<Character, AlphaNonHex>(40);
        for (char c = 'g'; c <= 'z'; c++) {
            final char lowerCase = Character.toLowerCase(c);
            final char upperCase = Character.toUpperCase(c);
            instances.put(lowerCase, new AlphaNonHex(lowerCase));
            instances.put(upperCase, new AlphaNonHex(upperCase));
        }
    }

    public static AlphaNonHex getInstance(final char character) {
        return instances.get(character);
    }

    private AlphaNonHex(final char character) {
        super(character);
    }

}
