package org.silnith.grammar.example;

import org.silnith.grammar.TerminalSymbol;

/**
 * An example implementation class for terminals that demonstrates
 * how to use an enumeration instead of a normal class.
 */
public enum Terminals implements TerminalSymbol {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    EOF;
    
    @Override
    public Type getType() {
        return Type.TERMINAL;
    }
    
}
