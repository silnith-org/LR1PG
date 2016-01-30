package org.silnith.grammar;

import java.util.List;


public class Item {
    
    private final NonTerminalSymbol leftHandSide;
    
    private final Production rightHandSide;
    
    private final int parserPosition;
    
    private final int hashCode;
    
    public Item(final NonTerminalSymbol leftHandSide, final Production rightHandSide, final int parserPosition) {
        super();
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.parserPosition = parserPosition;
        this.hashCode = this.leftHandSide.hashCode() ^ this.rightHandSide.hashCode()
                ^ Integer.valueOf(this.parserPosition).hashCode();
    }
    
    public boolean isComplete() {
        return parserPosition >= rightHandSide.getSymbols().size();
    }
    
    public Symbol getNextSymbol() {
        return rightHandSide.getSymbols().get(parserPosition);
    }
    
    public NonTerminalSymbol getLeftHandSide() {
        return leftHandSide;
    }
    
    public Production getRightHandSide() {
        return rightHandSide;
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
            return leftHandSide.equals(other.leftHandSide) && rightHandSide.equals(other.rightHandSide)
                    && parserPosition == other.parserPosition;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        final List<Symbol> symbols = rightHandSide.getSymbols();
        final List<Symbol> consumedSymbols = symbols.subList(0, parserPosition);
        final List<Symbol> unconsumedSymbols = symbols.subList(parserPosition, symbols.size());
        return "{" + leftHandSide + " -> " + consumedSymbols + " . " + unconsumedSymbols + "}";
    }
    
}
