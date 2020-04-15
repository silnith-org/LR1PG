package org.silnith.grammar;

/**
 * An object that identifies a unique non-terminal symbol. Each non-terminal should have a separate instance of this.
 * Instances of {@link NonTerminalSymbol} that are {@link #equals(Object)} identify the same non-terminal.
 */
public interface NonTerminalSymbol extends Symbol {
}
