package org.silnith.grammar.bnf;

import org.silnith.grammar.TerminalSymbol;


public enum AsciiTerminal implements TerminalSymbol {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z,
    zero, one, two, three, four, five, six, seven, eight, nine,
    bar, space, bang, hash, dollar, percent, and, leftparen, rightparen, star, plus,
    comma, dash, dot, slash, colon, semi, lt, eq, gt, question, at, leftbracket, backslash, rightbracket,
    hat, underscope, backtick, leftbrace, rightbrace, tilde, apos, quote, eol, eof;
}
