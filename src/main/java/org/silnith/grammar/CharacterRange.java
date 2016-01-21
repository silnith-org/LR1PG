package org.silnith.grammar;

import java.util.HashSet;
import java.util.Set;

public class CharacterRange extends Terminal {

	private final char rangeStart;

	private final char rangeEnd;

	public CharacterRange(final String name, final char start, final char end) {
		super(name);
		this.rangeStart = start;
		this.rangeEnd = end;
	}

	@Override
	public boolean matches(final char character) {
		return (character >= this.rangeStart) && (character <= this.rangeEnd);
	}

	@Override
	public Set<Character> getFirstSet() {
		final HashSet<Character> set = new HashSet<>();
		for (char c = rangeStart; c <= rangeEnd; c++) {
			set.add(c);
		}
		return set;
	}

	@Override
	public int hashCode() {
		return Character.valueOf(rangeStart).hashCode() ^ Character.valueOf(rangeEnd).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CharacterRange) {
			final CharacterRange other = (CharacterRange) obj;
			return rangeStart == other.rangeStart && rangeEnd == other.rangeEnd;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getName() + "[#" + (int) rangeStart + "-#" + (int) rangeEnd + "]";
	}

}
