package org.silnith.grammar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NotCharacterLiteral extends Terminal {

	final Collection<Character> notCharacters;

	public NotCharacterLiteral(final String name, final char... notCharacters) {
		super(name);
		this.notCharacters = new HashSet<>();
		for (final char ch : notCharacters) {
			this.notCharacters.add(ch);
		}
	}

	@Override
	public boolean matches(final char character) {
		return !notCharacters.contains(character);
	}

	@Override
	public Set<Character> getFirstSet() {
		final Set<Character> set = new HashSet<>(ALL_CHARACTERS);
		set.removeAll(notCharacters);
		return set;
	}

	@Override
	public int hashCode() {
		return notCharacters.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof NotCharacterLiteral) {
			final NotCharacterLiteral other = (NotCharacterLiteral) obj;
			return notCharacters.equals(other.notCharacters);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getName() + "[^" + notCharacters + "]";
	}

	private static final Set<Character> ALL_CHARACTERS;

	static {
		ALL_CHARACTERS = new HashSet<>();
		ALL_CHARACTERS.add((char) 0x9);
		ALL_CHARACTERS.add((char) 0xA);
		ALL_CHARACTERS.add((char) 0xD);
		for (char ch = 0x20; ch < 0xD7FF; ch++) {
			ALL_CHARACTERS.add(ch);
		}
		for (char ch = 0xE000; ch < 0xFFFD; ch++) {
			ALL_CHARACTERS.add(ch);
		}
		// #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
	}

}
