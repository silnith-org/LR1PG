package org.silnith.grammar.uri;

import org.silnith.grammar.Token;

public abstract class UriTerminal implements Token {
    
    private final char character;

    protected UriTerminal(final char character) {
        super();
        this.character = character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }

}
