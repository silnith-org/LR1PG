package org.silnith.grammar;

/**
 * An object that identifies the type of a terminal.  Every {@link Token} whose {@link Token#getSymbol()} is
 * {@link Token#equals(Object)} is treated as indistinguishable by the {@link Grammar}.
 */
public interface TerminalSymbol extends Symbol {
}
