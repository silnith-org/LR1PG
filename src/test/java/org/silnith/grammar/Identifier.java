package org.silnith.grammar;

import java.util.Collections;
import java.util.Set;


public class Identifier extends Terminal {
    
    public Identifier(final String name) {
        super(name);
    }
    
    @Override
    public boolean matches(final char character) {
        return false;
    }
    
    @Override
    public Set<Character> getFirstSet() {
        return Collections.singleton(getName().charAt(0));
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Identifier) {
            final Identifier other = (Identifier) obj;
            return getName().equals(other.getName());
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
