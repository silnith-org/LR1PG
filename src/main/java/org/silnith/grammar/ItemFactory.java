package org.silnith.grammar;

class ItemFactory extends WeakCanonicalFactory<Item> {
    
    public ItemFactory() {
        super();
    }
    
    public Item createItem(final NonTerminalSymbol leftHandSide, final Production rightHandSide,
            final int parserPosition) {
        return valueOf(new Item(leftHandSide, rightHandSide, parserPosition));
    }
    
}
