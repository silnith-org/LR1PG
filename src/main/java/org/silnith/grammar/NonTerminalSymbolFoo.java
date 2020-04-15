package org.silnith.grammar;

public class NonTerminalSymbolFoo implements NonTerminalSymbolMatch {

    private final String name;

    public NonTerminalSymbolFoo(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
