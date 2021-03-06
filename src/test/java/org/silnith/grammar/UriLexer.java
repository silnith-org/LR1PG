package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silnith.grammar.uri.token.AlphaHex;
import org.silnith.grammar.uri.token.AlphaNonHex;
import org.silnith.grammar.uri.token.Ampersand;
import org.silnith.grammar.uri.token.Apostrophe;
import org.silnith.grammar.uri.token.Asterisk;
import org.silnith.grammar.uri.token.AtSign;
import org.silnith.grammar.uri.token.Colon;
import org.silnith.grammar.uri.token.Comma;
import org.silnith.grammar.uri.token.Digit;
import org.silnith.grammar.uri.token.Dollar;
import org.silnith.grammar.uri.token.Equals;
import org.silnith.grammar.uri.token.ExclamationMark;
import org.silnith.grammar.uri.token.ForwardSlash;
import org.silnith.grammar.uri.token.Hyphen;
import org.silnith.grammar.uri.token.LeftBracket;
import org.silnith.grammar.uri.token.LeftParenthesis;
import org.silnith.grammar.uri.token.NumberSign;
import org.silnith.grammar.uri.token.Percent;
import org.silnith.grammar.uri.token.Period;
import org.silnith.grammar.uri.token.Plus;
import org.silnith.grammar.uri.token.QuestionMark;
import org.silnith.grammar.uri.token.RightBracket;
import org.silnith.grammar.uri.token.RightParenthesis;
import org.silnith.grammar.uri.token.Semicolon;
import org.silnith.grammar.uri.token.Tilde;
import org.silnith.grammar.uri.token.Underscore;
import org.silnith.grammar.uri.token.UriTerminal;
import org.silnith.grammar.uri.token.UriTerminalType;

public class UriLexer implements Lexer<UriTerminalType> {

    private static class EndOfFile extends UriTerminal {

        private EndOfFile() {
            super((char) 0);
        }

        @Override
        public UriTerminalType getSymbol() {
            return UriTerminalType.EndOfFile;
        }

    }

    public static final UriTerminal endOfFileSymbol = new EndOfFile();
    
    private static UriTerminal getTerminal(final char c) {
        switch (c) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            return Digit.getInstance(c);
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
            return AlphaHex.getInstance(c);
        case '&':
            return Ampersand.getInstance();
        case '\'':
            return Apostrophe.getInstance();
        case '*':
            return Asterisk.getInstance();
        case '@':
            return AtSign.getInstance();
        case ':':
            return Colon.getInstance();
        case ',':
            return Comma.getInstance();
        case '$':
            return Dollar.getInstance();
        case '=':
            return Equals.getInstance();
        case '!':
            return ExclamationMark.getInstance();
        case '/':
            return ForwardSlash.getInstance();
        case '[':
            return LeftBracket.getInstance();
        case '(':
            return LeftParenthesis.getInstance();
        case '#':
            return NumberSign.getInstance();
        case '+':
            return Plus.getInstance();
        case '?':
            return QuestionMark.getInstance();
        case ']':
            return RightBracket.getInstance();
        case ')':
            return RightParenthesis.getInstance();
        case ';':
            return Semicolon.getInstance();
        case '-':
            return Hyphen.getInstance();
        case '.':
            return Period.getInstance();
        case '_':
            return Underscore.getInstance();
        case '~':
            return Tilde.getInstance();
        case '%':
            return Percent.getInstance();
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
            return AlphaNonHex.getInstance(c);
        default:
            throw new RuntimeException("Unexpected character: " + c);
        }
    }

    private final List<Token<UriTerminalType>> terminals;

    public UriLexer(final String string) {
        super();            
        this.terminals = new ArrayList<>(string.length());
        for (final char c : string.toCharArray()) {
            this.terminals.add(getTerminal(c));
        }
        this.terminals.add(endOfFileSymbol);
    }

    @Override
    public Iterator<Token<UriTerminalType>> iterator() {
        return terminals.iterator();
    }
    
}