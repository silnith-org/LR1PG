package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silnith.grammar.uri.AlphaHex;
import org.silnith.grammar.uri.AlphaNonHex;
import org.silnith.grammar.uri.Ampersand;
import org.silnith.grammar.uri.Apostrophe;
import org.silnith.grammar.uri.Asterisk;
import org.silnith.grammar.uri.AtSign;
import org.silnith.grammar.uri.Colon;
import org.silnith.grammar.uri.Comma;
import org.silnith.grammar.uri.Digit;
import org.silnith.grammar.uri.Dollar;
import org.silnith.grammar.uri.Equals;
import org.silnith.grammar.uri.ExclamationMark;
import org.silnith.grammar.uri.ForwardSlash;
import org.silnith.grammar.uri.Hyphen;
import org.silnith.grammar.uri.LeftBracket;
import org.silnith.grammar.uri.LeftParenthesis;
import org.silnith.grammar.uri.NumberSign;
import org.silnith.grammar.uri.Percent;
import org.silnith.grammar.uri.Period;
import org.silnith.grammar.uri.Plus;
import org.silnith.grammar.uri.QuestionMark;
import org.silnith.grammar.uri.RightBracket;
import org.silnith.grammar.uri.RightParenthesis;
import org.silnith.grammar.uri.Semicolon;
import org.silnith.grammar.uri.Tilde;
import org.silnith.grammar.uri.Underscore;
import org.silnith.grammar.uri.UriTerminal;
import org.silnith.grammar.uri.UriTerminalType;

public class UriLexer implements Lexer {

    private static class EndOfFile extends UriTerminal {

        private EndOfFile() {
            super((char) 0);
        }

        @Override
        public TerminalSymbolMatch getMatch() {
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

    private final List<Terminal> terminals;

    public UriLexer(final String string) {
        super();            
        this.terminals = new ArrayList<>(string.length());
        for (final char c : string.toCharArray()) {
            this.terminals.add(getTerminal(c));
        }
        this.terminals.add(endOfFileSymbol);
    }

    @Override
    public Iterator<Terminal> iterator() {
        return terminals.iterator();
    }
    
}