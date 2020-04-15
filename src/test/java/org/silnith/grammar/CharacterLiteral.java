package org.silnith.grammar;

public class CharacterLiteral implements TerminalSymbolMatch {
    
    private final String name;
    private final char character;
    
    public CharacterLiteral(final String name, final char character) {
        super();
        this.name = name;
        this.character = character;
    }
    
    public CharacterLiteral(final char character) {
        this(String.valueOf(character), character);
    }
    
    @Override
    public int hashCode() {
        return Character.valueOf(character).hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CharacterLiteral) {
            final CharacterLiteral other = (CharacterLiteral) obj;
            return character == other.character;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "'" + name + "'#" + (int) character;
    }
    
}
