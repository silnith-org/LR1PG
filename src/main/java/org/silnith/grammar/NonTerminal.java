package org.silnith.grammar;

public class NonTerminal extends GenericSymbol implements NonTerminalSymbol {

	public static final NonTerminal START = create("START SYMBOL");

	public static NonTerminal create(final String name) {
		return new NonTerminal(name);
	}

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
