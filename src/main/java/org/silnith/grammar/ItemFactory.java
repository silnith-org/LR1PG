package org.silnith.grammar;

public class ItemFactory extends CanonicalFactory<Item> {
    
    public ItemFactory() {
        super();
    }
    
    public Item createItem(final NonTerminalSymbol leftHandSide, final Production rightHandSide,
            final int parserPosition) {
        return valueOf(new Item(leftHandSide, rightHandSide, parserPosition));
    }
    
}
