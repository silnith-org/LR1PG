package org.silnith.grammar;

import java.util.Collections;
import java.util.Set;

public class CharacterLiteral extends Terminal {

	private final char character;

	public CharacterLiteral(final String name, final char character) {
		super(name);
		this.character = character;
	}

	public CharacterLiteral(final char character) {
		this(String.valueOf(character), character);
	}

	@Override
	public boolean matches(final char character) {
		return this.character == character;
	}

	@Override
	public Set<Character> getFirstSet() {
		return Collections.singleton(character);
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
		return "'" + getName() + "'#" + (int) character;
	}

}
