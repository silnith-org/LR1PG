package org.silnith.grammar.uri;

import org.silnith.grammar.Terminal;

public abstract class UriTerminal implements Terminal {
    
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
