package org.silnith.grammar;

import java.util.List;
import java.util.Objects;

/**
 * An item represents a production coupled with a parse position.  It represents one node in the
 * <abbr title="non-deterministic finite state automata">NFA</abbr>.  A node in the
 * <abbr title="deterministic finite state automata">DFA</abbr> is composed of a set of these.
 */
class Item {
    
    private final NonTerminalSymbol target;
    
    private final Production production;
    
    private final int parserPosition;
    
    private final int hashCode;
    
    public Item(final NonTerminalSymbol target, final Production production, final int parserPosition) {
        super();
        if (target == null || production == null) {
            throw new IllegalArgumentException();
        }
        if (parserPosition < 0) {
            throw new IllegalArgumentException();
        }
        if (parserPosition > production.getSymbols().size()) {
            throw new IllegalArgumentException();
        }
        this.target = target;
        this.production = production;
        this.parserPosition = parserPosition;
        this.hashCode = Objects.hash(this.target, this.production, this.parserPosition);
    }
    
    /**
     * Returns {@code true} if the {@link #getParserPosition() parser position} is beyond all symbols in the
     * {@link #getProduction() right-hand side}.
     * 
     * @return {@code true} if the production is complete
     */
    public boolean isComplete() {
        return parserPosition == production.getSymbols().size();
    }
    
    /**
     * Returns the next symbol to be consumed.  Will throw an exception if {@link #isComplete()} returns {@code true}.
     * 
     * @return the next symbol to be consumed
     */
    public Symbol getNextSymbol() {
        return production.getSymbols().get(parserPosition);
    }
    
    public NonTerminalSymbol getTarget() {
        return target;
    }
    
    public Production getProduction() {
        return production;
    }
    
    public int getParserPosition() {
        return parserPosition;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Item) {
            final Item other = (Item) obj;
            if (hashCode != other.hashCode) {
                return false;
            }
            return target.equals(other.target) && production.equals(other.production)
                    && parserPosition == other.parserPosition;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        final List<Symbol> symbols = production.getSymbols();
        final List<Symbol> consumedSymbols = symbols.subList(0, parserPosition);
        final List<Symbol> unconsumedSymbols = symbols.subList(parserPosition, symbols.size());
        return "{" + target + " -> " + consumedSymbols + " . " + unconsumedSymbols + "}";
    }
    
}
