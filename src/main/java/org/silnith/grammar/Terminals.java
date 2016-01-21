package org.silnith.grammar;

public enum Terminals implements TerminalSymbol {
	A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, EOF;

	@Override
	public Type getType() {
		return Type.TERMINAL;
	}

}
