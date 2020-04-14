package org.silnith.grammar.uri;

import org.silnith.grammar.Symbol;
import org.silnith.grammar.TerminalSymbol;
import org.silnith.grammar.Symbol.Type;

public abstract class UriTerminal implements TerminalSymbol {

    private final char character;

    protected UriTerminal(final char character) {
        super();
        this.character = character;
    }

    @Override
    public Symbol.Type getType() {
        return Symbol.Type.TERMINAL;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }

}
