package org.silnith.grammar;

/**
 * An implementation of {@link NonTerminalSymbol}.  Most clients will not need to implement the interface directly,
 * and are encouraged to use {@link Grammar#getNonTerminalSymbol(String)}.
 */
public class NonTerminal extends GenericSymbol implements NonTerminalSymbol {
    
    /**
     * A convenience symbol.
     */
    public static final NonTerminal START = create("START SYMBOL");
    
    /**
     * Creates a new non-terminal symbol.  Clients should prefer {@link Grammar#getNonTerminalSymbol(String)}.
     * 
     * @param name the name of the non-terminal symbol
     * @return a new non-terminal symbol
     */
    public static NonTerminal create(final String name) {
        return new NonTerminal(name);
    }
    
    /**
     * Creates a new non-terminal symbol.
     * 
     * @param name the name of the non-terminal symbol
     */
    public NonTerminal(final String name) {
        super(name);
    }
    
    @Override
    public Type getType() {
        return Type.NON_TERMINAL;
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof NonTerminal) {
            final NonTerminal other = (NonTerminal) obj;
            return getName().equals(other.getName());
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return '<' + getName() + '>';
    }
    
}
