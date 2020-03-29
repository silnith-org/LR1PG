package org.silnith.grammar;

public class CharacterTerminal extends GenericSymbol implements TerminalSymbol {
	
	private final char character;

	public CharacterTerminal(final char character) {
		super(String.valueOf(character));
		this.character = character;
	}

	@Override
	public Type getType() {
		return Type.TERMINAL;
	}

	@Override
	public int hashCode() {
		return Character.valueOf(character).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CharacterTerminal)) {
			return false;
		}
		final CharacterTerminal other = (CharacterTerminal) obj;
		return character == other.character;
	}

}