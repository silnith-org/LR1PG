package org.silnith.grammar;

import java.util.Objects;

/**
 * An implementation of {@link NonTerminalSymbol}.  Clients of the API should prefer {@link Grammar#getNonTerminalSymbol(String)}.
 */
public class NonTerminal implements NonTerminalSymbol {

    private final String name;

    public NonTerminal(final String name) {
        super();
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NonTerminal)) {
            return false;
        }
        final NonTerminal other = (NonTerminal) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
