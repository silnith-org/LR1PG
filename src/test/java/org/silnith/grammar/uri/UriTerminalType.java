package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public enum UriTerminalType implements TerminalSymbolMatch {
    AlphaHex,
    AlphaNonHex,
    Ampersand,
    Apostrophe,
    Asterisk,
    AtSign,
    Colon,
    Comma,
    Digit,
    Dollar,
    EndOfFile,
    Equals,
    ExclamationMark,
    ForwardSlash,
    Hyphen,
    LeftBracket,
    LeftParenthesis,
    NumberSign,
    Percent,
    Period,
    Plus,
    QuestionMark,
    RightBracket,
    RightParenthesis,
    Semicolon,
    Tilde,
    Underscore;
}