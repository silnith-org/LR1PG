package org.silnith.grammar;

public enum UnicodeTerminalSymbols implements TerminalSymbol {
    /*
     * All the terminal symbols reachable from the original production
     * "document".
     */
    /**
     * U+0009 CHARACTER TABULATION (tab)
     */
    tab("#x9", '\t'),
    /**
     * U+000A LINE FEED (LF)
     */
    lineFeed("#xA", '\n'),
    /**
     * U+000D CARRIAGE RETURN (CR)
     */
    carriageReturn("#xD", '\r'),
    /**
     * U+0020 SPACE
     */
    space("#x20", ' '),
    /**
     * U+0021 EXCLAMATION MARK (!)
     */
    exclamationMark('!'),
    /**
     * U+0022 QUOTATION MARK (")
     */
    quotationMark('"'),
    /**
     * U+0023 NUMBER SIGN (#)
     */
    numberSign('#'),
    /**
     * U+0024 DOLLAR SIGN ($)
     */
    dollarSign('$'),
    /**
     * U+0025 PERCENTAGE SIGN (%)
     */
    percentSign('%'),
    /**
     * U+0026 AMPERSAND (&)
     */
    ampersand('&'),
    /**
     * U+0027 APOSTROPHE (')
     */
    apostrophe('\''),
    /**
     * U+0028 LEFT PARENTHESIS (()
     */
    leftParenthesis('('),
    /**
     * U+0029 RIGHT PARENTHESIS ())
     */
    rightParenthesis(')'),
    /**
     * U+002A ASTERISK (*)
     */
    asterisk('*'),
    /**
     * U+002B PLUS SIGN (+)
     */
    plusSign('+'),
    /**
     * U+002C COMMA (,)
     */
    comma(','),
    /**
     * U+002D HYPHEN-MINUS (-)
     */
    hyphenMinus('-'),
    /**
     * U+002E FULL STOP (.)
     */
    fullStop('.'),
    /**
     * U+002F SOLIDUS (/)
     */
    solidus('/'),
    /**
     * U+0030 DIGIT ZERO (0)
     */
    digitZero('0'),
    /**
     * U+0031 DIGIT ONE (1)
     */
    digitOne('1'),
    /**
     * U+0032 DIGIT TWO (2)
     */
    digitTwo('2'),
    /**
     * U+0033 DIGIT THREE (3)
     */
    digitThree('3'),
    /**
     * U+0034 DIGIT FOUR (4)
     */
    digitFour('4'),
    /**
     * U+0035 DIGIT FIVE (5)
     */
    digitFive('5'),
    /**
     * U+0036 DIGIT SIX (6)
     */
    digitSix('6'),
    /**
     * U+0037 DIGIT SEVEN (7)
     */
    digitSeven('7'),
    /**
     * U+0038 DIGIT EIGHT (8)
     */
    digitEight('8'),
    /**
     * U+0039 DIGIT NINE (9)
     */
    digitNine('9'),
    /**
     * U+003A COLON (:)
     */
    colon(':'),
    /**
     * U+003B SEMICOLON (;)
     */
    semicolon(';'),
    /**
     * U+003C LESS-THAN SIGN (<)
     */
    lessThanSign('<'),
    /**
     * U+003D EQUALS SIGN (=)
     */
    equalsSign('='),
    /**
     * U+003E GREATER-THAN SIGN (>)
     */
    greaterThanSign('>'),
    /**
     * U+003F QUESTION MARK (?)
     */
    questionMark('?'),
    /**
     * U+0040 COMMERCIAL AT (@)
     */
    atSymbol('@'),
    /**
     * U+0041 LATIN CAPITAL LETTER A (A) &ndash; U+0046 LATIN CAPITAL LETTER F
     * (F)
     */
//	uppercaseHexDigit('A', 'F'),
    /**
     * U+0041 LATIN CAPITAL LETTER A (A)
     */
    capitalA('A'),
    /**
     * U+0042 LATIN CAPITAL LETTER B (B)
     */
    capitalB('B'),
    /**
     * U+0043 LATIN CAPITAL LETTER C (C)
     */
    capitalC('C'),
    /**
     * U+0044 LATIN CAPITAL LETTER D (D)
     */
    capitalD('D'),
    /**
     * U+0045 LATIN CAPITAL LETTER E (E)
     */
    capitalE('E'),
    /**
     * U+0046 LATIN CAPITAL LETTER F (F)
     */
    capitalF('F'),
    /**
     * U+0047 LATIN CAPITAL LETTER G (G)
     */
    capitalG('G'),
    /**
     * U+0048 LATIN CAPITAL LETTER H (H)
     */
    capitalH('H'),
    /**
     * U+0049 LATIN CAPITAL LETTER I (I)
     */
    capitalI('I'),
    /**
     * U+004A LATIN CAPITAL LETTER J (J)
     */
    capitalJ('J'),
    /**
     * U+004B LATIN CAPITAL LETTER K (K)
     */
    capitalK('K'),
    /**
     * U+004C LATIN CAPITAL LETTER L (L)
     */
    capitalL('L'),
    /**
     * U+004D LATIN CAPITAL LETTER M (M)
     */
    capitalM('M'),
    /**
     * U+004E LATIN CAPITAL LETTER N (N)
     */
    capitalN('N'),
    /**
     * U+004F LATIN CAPITAL LETTER O (O)
     */
    capitalO('O'),
    /**
     * U+0050 LATIN CAPITAL LETTER P (P)
     */
    capitalP('P'),
    /**
     * U+0051 LATIN CAPITAL LETTER Q (Q)
     */
    capitalQ('Q'),
    /**
     * U+0052 LATIN CAPITAL LETTER R (R)
     */
    capitalR('R'),
    /**
     * U+0053 LATIN CAPITAL LETTER S (S)
     */
    capitalS('S'),
    /**
     * U+0054 LATIN CAPITAL LETTER T (T)
     */
    capitalT('T'),
    /**
     * U+0055 LATIN CAPITAL LETTER U (U)
     */
    capitalU('U'),
    /**
     * U+0056 LATIN CAPITAL LETTER V (V)
     */
    capitalV('V'),
    /**
     * U+0057 LATIN CAPITAL LETTER W (W)
     */
    capitalW('W'),
    /**
     * U+0058 LATIN CAPITAL LETTER X (X)
     */
    capitalX('X'),
    /**
     * U+0059 LATIN CAPITAL LETTER Y (Y)
     */
    capitalY('Y'),
    /**
     * U+005A LATIN CAPITAL LETTER Z (Z)
     */
    capitalZ('Z'),
    /**
     * &ndash; U+005A LATIN CAPITAL LETTER Z (Z)
     */
//	uppercaseASCII('G', 'Z'),
    /**
     * U+005B LEFT SQUARE BRACKET ([)
     */
    leftBracket('['),
    /**
     * U+005C REVERSE SOLIDUS (\)
     */
    reverseSolidus('\\'),
    /**
     * U+005D RIGHT SQUARE BRACKET (])
     */
    rightBracket(']'),
    /**
     * U+005E CIRCUMFLEX ACCENT (^)
     */
    circumflexAccent('^'),
    /**
     * U+005F LOW LINE (_)
     */
    lowLine('_'),
    /**
     * U+0060 GRAVE ACCENT (`)
     */
    graveAccent('`'),
    /**
     * U+0061 LATIN SMALL LETTER A (a) &ndash; U+0066 LATIN SMALL LETTER F (f)
     */
//	lowercaseHexDigit('a', 'f'),
    /**
     * U+0061 LATIN SMALL LETTER A (a)
     */
    smallA('a'),
    /**
     * U+0062 LATIN SMALL LETTER B (b)
     */
    smallB('b'),
    /**
     * U+0063 LATIN SMALL LETTER C (c)
     */
    smallC('c'),
    /**
     * U+0064 LATIN SMALL LETTER D (d)
     */
    smallD('d'),
    /**
     * U+0065 LATIN SMALL LETTER E (e)
     */
    smallE('e'),
    /**
     * U+0066 LATIN SMALL LETTER F (f)
     */
    smallF('f'),
    /**
     * U+0067 LATIN SMALL LETTER G (g)
     */
    smallG('g'),
    /**
     * U+0068 LATIN SMALL LETTER H (h)
     */
    smallH('h'),
    /**
     * U+0069 LATIN SMALL LETTER I (i)
     */
    smallI('i'),
    /**
     * U+006A LATIN SMALL LETTER J (j)
     */
    smallJ('j'),
    /**
     * U+006B LATIN SMALL LETTER K (k)
     */
    smallK('k'),
    /**
     * U+006C LATIN SMALL LETTER L (l)
     */
    smallL('l'),
    /**
     * U+006D LATIN SMALL LETTER M (m)
     */
    smallM('m'),
    /**
     * U+006E LATIN SMALL LETTER N (n)
     */
    smallN('n'),
    /**
     * U+006F LATIN SMALL LETTER O (o)
     */
    smallO('o'),
    /**
     * U+0070 LATIN SMALL LETTER P (p)
     */
    smallP('p'),
    /**
     * U+0071 LATIN SMALL LETTER Q (q)
     */
    smallQ('q'),
    /**
     * U+0072 LATIN SMALL LETTER R (r)
     */
    smallR('r'),
    /**
     * U+0073 LATIN SMALL LETTER S (s)
     */
    smallS('s'),
    /**
     * U+0074 LATIN SMALL LETTER T (t)
     */
    smallT('t'),
    /**
     * U+0075 LATIN SMALL LETTER U (u)
     */
    smallU('u'),
    /**
     * U+0076 LATIN SMALL LETTER V (v)
     */
    smallV('v'),
    /**
     * U+0077 LATIN SMALL LETTER W (w)
     */
    smallW('w'),
    /**
     * U+0078 LATIN SMALL LETTER X (x)
     */
    smallX('x'),
    /**
     * U+0079 LATIN SMALL LETTER Y (y)
     */
    smallY('y'),
    /**
     * U+007A LATIN SMALL LETTER Z (z)
     */
    smallZ('z'),
    /**
     * &ndash; U+007A LATIN SMALL LETTER Z (z)
     */
//	lowercaseASCII('g', 'z'),
    /**
     * U+007B LEFT CURLY BRACKET ({)
     */
    leftCurlyBrace('{'),
    /**
     * U+007C VERTICAL LINE (|)
     */
    verticalBar('|'),
    /**
     * U+007D RIGHT CURLY BRACKET (})
     */
    rightCurlyBrace('}'),
    /**
     * U+007E TILDE (~)
     */
    tilde('~'),
    /**
     * U+007F DELETE
     */
    delete('\u007F'),
    // 80 = control
    /**
     * U+0080 &ndash; U+D7FF
     */
    belowSurrogates('\u0080', '\uD7FF'),
    /**
     * U+E000 &ndash; U+FFFD
     */
    aboveSurrogates('\uE000', '\uFFFD'),
    supplementaryMultilingualPlane(0x10000, 0x1FFFF),
    supplementaryIdeographicPlane(0x20000, 0x2FFFF),
    supplementaryUnassigned(0x30000, 0xDFFFF),
    supplementarySpecialPurposePlane(0xE0000, 0xEFFFF),
    supplementaryPrivateUsePlanes(0xF0000, 0x10FFFF),
    EOF("eof", (char) 0xFFFF);
    
    private final String name;
    
    private final char character;
    
    private UnicodeTerminalSymbols(final String name, final char character) {
        this.name = name;
        this.character = character;
    }
    
    private UnicodeTerminalSymbols(final char character) {
        this.name = String.valueOf(character);
        this.character = character;
    }
    
    private UnicodeTerminalSymbols(final char start, final char end) {
        this.name = start + " - " + end;
        this.character = start;
    }
    
    private UnicodeTerminalSymbols(final int start, final int end) {
        this.name = start + " - " + end;
        this.character = '\uFFFD';
    }
    
    public String getName() {
        return name;
    }
    
    public char getCharacter() {
        return character;
    }
    
    @Override
    public Type getType() {
        return Type.TERMINAL;
    }
    
}
