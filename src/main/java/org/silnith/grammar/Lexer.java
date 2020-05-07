package org.silnith.grammar;

/**
 * Reads an input and emits a stream of {@link Token}s.  {@link Parser}s rely on a lexer to map the potentially large
 * set of possible input values to a smaller set of token types.
 * 
 * @param <T> the concrete type of terminal symbols
 */
public interface Lexer<T extends TerminalSymbol> extends Iterable<Token<T>> {
}
