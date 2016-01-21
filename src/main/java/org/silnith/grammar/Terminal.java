package org.silnith.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Terminal extends GenericSymbol implements TerminalSymbol {

	public static CharacterLiteral[] getStringLiteral(final String str) {
		final List<CharacterLiteral> symbols = new ArrayList<>();
		for (final char character : str.toCharArray()) {
			symbols.add(new CharacterLiteral(String.valueOf(character), character));
		}
		return symbols.toArray(new CharacterLiteral[str.length()]);
	}

	public static CharacterRange getCharacterRange(final char start, final char end) {
		return new CharacterRange("[" + start + "-" + end + "]", start, end);
	}

	public static NotCharacterLiteral getNotCharacter(final String name, final char... notCharacters) {
		return new NotCharacterLiteral(name, notCharacters);
	}

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
