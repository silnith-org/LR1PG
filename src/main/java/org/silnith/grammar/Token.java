package org.silnith.grammar;

/**
 * A token emitted by a {@link Lexer}.
 * 
 * @param <T> the concrete type of identifiers for terminal symbols
 */
public interface Token<T extends TerminalSymbol> {

    /**
     * Returns the symbol type.  Different terminals may return the same symbol type if the grammar does not need to
     * distinguish between them while parsing.  The {@link ProductionHandler}s receive the actual terminals and have
     * access to any additional information attached to them, so the generated AST can differentiate terminals.
     * 
     * @return the symbol type
     */
    T getSymbol();

}