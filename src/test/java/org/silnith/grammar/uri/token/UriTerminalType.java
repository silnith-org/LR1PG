package org.silnith.grammar.uri.token;

import org.silnith.grammar.TerminalSymbol;

public enum UriTerminalType implements TerminalSymbol {
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