package org.silnith.grammar;

import java.util.Arrays;
import java.util.List;

public class Production {

	private final ProductionHandler productionHandler;

	private final List<Symbol> symbols;

	private final int hashCode;

	public Production(final ProductionHandler productionHandler, final Symbol... symbols) {
		super();
		this.productionHandler = productionHandler;
		this.symbols = Arrays.asList(symbols);
		this.hashCode = this.symbols.hashCode();
	}

	public ProductionHandler getProductionHandler() {
		// TODO: include in hashCode() and equals()
		return productionHandler;
	}

	public List<Symbol> getSymbols() {
		return symbols;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Production) {
			final Production other = (Production) obj;
			if (hashCode != other.hashCode) {
				return false;
			}
			return symbols.equals(other.symbols);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.valueOf(symbols);
	}

}
