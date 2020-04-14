package org.silnith.grammar;

import java.util.Set;


public abstract class Terminal extends GenericSymbol implements TerminalSymbol {
    
    public Terminal(final String name) {
        super(name);
    }
    
    @Override
    public Type getType() {
        return Type.TERMINAL;
    }
    
    public abstract boolean matches(final char character);
    
    public abstract Set<Character> getFirstSet();
    
}
