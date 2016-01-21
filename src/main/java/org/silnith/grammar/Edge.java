package org.silnith.grammar;

public class Edge<T> {

	private final ItemSet<T> initialState;

	private final Symbol symbol;

	private final ItemSet<T> finalState;

	private final int hashCode;

	public Edge(final ItemSet<T> initialState, final Symbol symbol, final ItemSet<T> finalState) {
		super();
		this.initialState = initialState;
		this.symbol = symbol;
		this.finalState = finalState;
		this.hashCode = this.initialState.hashCode() ^ this.symbol.hashCode() ^ this.finalState.hashCode();
	}

	public ItemSet<T> getInitialState() {
		return initialState;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public ItemSet<T> getFinalState() {
		return finalState;
	}

	@Override
	public int hashCode() {
//		return initialState.hashCode() ^ symbol.hashCode() ^ finalState.hashCode();
		return hashCode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Edge) {
			@SuppressWarnings("unchecked")
			final Edge<?> other = (Edge<Object>) obj;
			return initialState.equals(other.initialState) && symbol.equals(other.symbol) && finalState.equals(other.finalState);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return initialState + " --> (" + symbol + ") --> " + finalState;
	}

}
