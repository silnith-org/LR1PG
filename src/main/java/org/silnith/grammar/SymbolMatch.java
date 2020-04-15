package org.silnith.grammar;

/**
 * An object that identifies a symbol by its type. {@link Grammar Grammars} are built out of {@link Production
 * Productions} that match symbols. {@link Parser Parsers} operate on streams of terminals. Parsers look up actions to
 * take based on the symbol match for the terminal. This allows a grammar to use a single symbol to represent a range of
 * possible terminals, and the parser can map each terminal back to the symbol that represents the range containing that
 * terminal.
 */
public interface SymbolMatch {

}
