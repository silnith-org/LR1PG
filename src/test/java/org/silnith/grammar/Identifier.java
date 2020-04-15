package org.silnith.grammar;

public class Identifier implements TerminalSymbol {
    
    private final String name;
    
    public Identifier(final String name) {
        super();
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Identifier) {
            final Identifier other = (Identifier) obj;
            return name.equals(other.name);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
