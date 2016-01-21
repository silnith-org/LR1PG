package org.silnith.grammar;

public abstract class GenericSymbol implements Symbol {

	private final String name;

	public GenericSymbol(final String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
