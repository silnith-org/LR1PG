package org.silnith.grammar.uri.token;

import org.silnith.grammar.Token;

public abstract class UriTerminal implements Token<UriTerminalType> {
    
    private final char character;

    protected UriTerminal(final char character) {
        super();
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }

}
