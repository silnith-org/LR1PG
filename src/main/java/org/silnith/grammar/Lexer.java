package org.silnith.grammar;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lexer implements Iterable<UnicodeTerminalSymbols> {

	private final Reader in;

	private int currentCharacter;

	public Lexer(final Reader in) {
		super();
		this.in = in;
		this.currentCharacter = 0;
	}

	@Override
	public Iterator<UnicodeTerminalSymbols> iterator() {
		return new UnicodeLexerIterator();
	}

	private class UnicodeLexerIterator implements Iterator<UnicodeTerminalSymbols> {

		private UnicodeLexerIterator() {
			super();
			try {
				int r = in.read();
				if (r == -1) {
					return;
				}
				if (Character.isHighSurrogate((char) r)) {
					int rr = in.read();
					if (rr == -1) {
						currentCharacter = -1;
						return;
					}
					if (Character.isLowSurrogate((char) rr)) {
						currentCharacter = Character.toCodePoint((char) r, (char) rr);
					} else {
						currentCharacter = r;
					}
				} else {
					currentCharacter = (char) r;
				}
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean hasNext() {
			return currentCharacter != -1;
		}

		@Override
		public UnicodeTerminalSymbols next() {
			final UnicodeTerminalSymbols symbol;
			switch (currentCharacter) {
			case -1: { symbol = UnicodeTerminalSymbols.EOF; } break;
			case 0x9: { symbol = UnicodeTerminalSymbols.tab; } break;
			case 0xA: { symbol = UnicodeTerminalSymbols.lineFeed; } break;
			case 0xD: { symbol = UnicodeTerminalSymbols.carriageReturn; } break;
			case 0x20: { symbol = UnicodeTerminalSymbols.space; } break;
			case 0x21: { symbol = UnicodeTerminalSymbols.exclamationMark; } break;
			case 0x22: { symbol = UnicodeTerminalSymbols.quotationMark; } break;
			case 0x23: { symbol = UnicodeTerminalSymbols.numberSign; } break;
			case 0x24: { symbol = UnicodeTerminalSymbols.dollarSign; } break;
			case 0x25: { symbol = UnicodeTerminalSymbols.percentSign; } break;
			case 0x26: { symbol = UnicodeTerminalSymbols.ampersand; } break;
			case 0x27: { symbol = UnicodeTerminalSymbols.apostrophe; } break;
			case 0x28: { symbol = UnicodeTerminalSymbols.leftParenthesis; } break;
			case 0x29: { symbol = UnicodeTerminalSymbols.rightParenthesis; } break;
			case 0x2A: { symbol = UnicodeTerminalSymbols.asterisk; } break;
			case 0x2B: { symbol = UnicodeTerminalSymbols.plusSign; } break;
			case 0x2C: { symbol = UnicodeTerminalSymbols.comma; } break;
			case 0x2D: { symbol = UnicodeTerminalSymbols.hyphenMinus; } break;
			case 0x2E: { symbol = UnicodeTerminalSymbols.fullStop; } break;
			case 0x2F: { symbol = UnicodeTerminalSymbols.solidus; } break;
			case 0x30: { symbol = UnicodeTerminalSymbols.digitZero; } break;
			case 0x31: { symbol = UnicodeTerminalSymbols.digitOne; } break;
			case 0x32: { symbol = UnicodeTerminalSymbols.digitTwo; } break;
			case 0x33: { symbol = UnicodeTerminalSymbols.digitThree; } break;
			case 0x34: { symbol = UnicodeTerminalSymbols.digitFour; } break;
			case 0x35: { symbol = UnicodeTerminalSymbols.digitFive; } break;
			case 0x36: { symbol = UnicodeTerminalSymbols.digitSix; } break;
			case 0x37: { symbol = UnicodeTerminalSymbols.digitSeven; } break;
			case 0x38: { symbol = UnicodeTerminalSymbols.digitEight; } break;
			case 0x39: { symbol = UnicodeTerminalSymbols.digitNine; } break;
			case 0x3A: { symbol = UnicodeTerminalSymbols.colon; } break;
			case 0x3B: { symbol = UnicodeTerminalSymbols.semicolon; } break;
			case 0x3C: { symbol = UnicodeTerminalSymbols.lessThanSign; } break;
			case 0x3D: { symbol = UnicodeTerminalSymbols.equalsSign; } break;
			case 0x3E: { symbol = UnicodeTerminalSymbols.greaterThanSign; } break;
			case 0x3F: { symbol = UnicodeTerminalSymbols.questionMark; } break;
			case 0x40: { symbol = UnicodeTerminalSymbols.atSymbol; } break;
			case 0x41: { symbol = UnicodeTerminalSymbols.capitalA; } break;
			case 0x42: { symbol = UnicodeTerminalSymbols.capitalB; } break;
			case 0x43: { symbol = UnicodeTerminalSymbols.capitalC; } break;
			case 0x44: { symbol = UnicodeTerminalSymbols.capitalD; } break;
			case 0x45: { symbol = UnicodeTerminalSymbols.capitalE; } break;
			case 0x46: { symbol = UnicodeTerminalSymbols.capitalF; } break;
			case 0x47: { symbol = UnicodeTerminalSymbols.capitalG; } break;
			case 0x48: { symbol = UnicodeTerminalSymbols.capitalH; } break;
			case 0x49: { symbol = UnicodeTerminalSymbols.capitalI; } break;
			case 0x4A: { symbol = UnicodeTerminalSymbols.capitalJ; } break;
			case 0x4B: { symbol = UnicodeTerminalSymbols.capitalK; } break;
			case 0x4C: { symbol = UnicodeTerminalSymbols.capitalL; } break;
			case 0x4D: { symbol = UnicodeTerminalSymbols.capitalM; } break;
			case 0x4E: { symbol = UnicodeTerminalSymbols.capitalN; } break;
			case 0x4F: { symbol = UnicodeTerminalSymbols.capitalO; } break;
			case 0x50: { symbol = UnicodeTerminalSymbols.capitalP; } break;
			case 0x51: { symbol = UnicodeTerminalSymbols.capitalQ; } break;
			case 0x52: { symbol = UnicodeTerminalSymbols.capitalR; } break;
			case 0x53: { symbol = UnicodeTerminalSymbols.capitalS; } break;
			case 0x54: { symbol = UnicodeTerminalSymbols.capitalT; } break;
			case 0x55: { symbol = UnicodeTerminalSymbols.capitalU; } break;
			case 0x56: { symbol = UnicodeTerminalSymbols.capitalV; } break;
			case 0x57: { symbol = UnicodeTerminalSymbols.capitalW; } break;
			case 0x58: { symbol = UnicodeTerminalSymbols.capitalX; } break;
			case 0x59: { symbol = UnicodeTerminalSymbols.capitalY; } break;
			case 0x5A: { symbol = UnicodeTerminalSymbols.capitalZ; } break;
			case 0x5B: { symbol = UnicodeTerminalSymbols.leftBracket; } break;
			case 0x5C: { symbol = UnicodeTerminalSymbols.reverseSolidus; } break;
			case 0x5D: { symbol = UnicodeTerminalSymbols.rightBracket; } break;
			case 0x5E: { symbol = UnicodeTerminalSymbols.circumflexAccent; } break;
			case 0x5F: { symbol = UnicodeTerminalSymbols.lowLine; } break;
			case 0x60: { symbol = UnicodeTerminalSymbols.graveAccent; } break;
			case 0x61: { symbol = UnicodeTerminalSymbols.smallA; } break;
			case 0x62: { symbol = UnicodeTerminalSymbols.smallB; } break;
			case 0x63: { symbol = UnicodeTerminalSymbols.smallC; } break;
			case 0x64: { symbol = UnicodeTerminalSymbols.smallD; } break;
			case 0x65: { symbol = UnicodeTerminalSymbols.smallE; } break;
			case 0x66: { symbol = UnicodeTerminalSymbols.smallF; } break;
			case 0x67: { symbol = UnicodeTerminalSymbols.smallG; } break;
			case 0x68: { symbol = UnicodeTerminalSymbols.smallH; } break;
			case 0x69: { symbol = UnicodeTerminalSymbols.smallI; } break;
			case 0x6A: { symbol = UnicodeTerminalSymbols.smallJ; } break;
			case 0x6B: { symbol = UnicodeTerminalSymbols.smallK; } break;
			case 0x6C: { symbol = UnicodeTerminalSymbols.smallL; } break;
			case 0x6D: { symbol = UnicodeTerminalSymbols.smallM; } break;
			case 0x6E: { symbol = UnicodeTerminalSymbols.smallN; } break;
			case 0x6F: { symbol = UnicodeTerminalSymbols.smallO; } break;
			case 0x70: { symbol = UnicodeTerminalSymbols.smallP; } break;
			case 0x71: { symbol = UnicodeTerminalSymbols.smallQ; } break;
			case 0x72: { symbol = UnicodeTerminalSymbols.smallR; } break;
			case 0x73: { symbol = UnicodeTerminalSymbols.smallS; } break;
			case 0x74: { symbol = UnicodeTerminalSymbols.smallT; } break;
			case 0x75: { symbol = UnicodeTerminalSymbols.smallU; } break;
			case 0x76: { symbol = UnicodeTerminalSymbols.smallV; } break;
			case 0x77: { symbol = UnicodeTerminalSymbols.smallW; } break;
			case 0x78: { symbol = UnicodeTerminalSymbols.smallX; } break;
			case 0x79: { symbol = UnicodeTerminalSymbols.smallY; } break;
			case 0x7A: { symbol = UnicodeTerminalSymbols.smallZ; } break;
			case 0x7B: { symbol = UnicodeTerminalSymbols.leftCurlyBrace; } break;
			case 0x7C: { symbol = UnicodeTerminalSymbols.verticalBar; } break;
			case 0x7D: { symbol = UnicodeTerminalSymbols.rightCurlyBrace; } break;
			case 0x7E: { symbol = UnicodeTerminalSymbols.tilde; } break;
			case 0x7F: { symbol = UnicodeTerminalSymbols.delete; } break;
			default: {
				if (currentCharacter >= 0x80 && currentCharacter <= 0xD7FF) {
					symbol = UnicodeTerminalSymbols.belowSurrogates;
				} else if (currentCharacter >= 0xE000 && currentCharacter <= 0xFFFD) {
					symbol = UnicodeTerminalSymbols.aboveSurrogates;
				} else if (currentCharacter >= 0x10000 && currentCharacter <= 0x1FFFF) {
					symbol = UnicodeTerminalSymbols.supplementaryMultilingualPlane;
				} else if (currentCharacter >= 0x20000 && currentCharacter <= 0x2FFFF) {
					symbol = UnicodeTerminalSymbols.supplementaryIdeographicPlane;
				} else if (currentCharacter >= 0x30000 && currentCharacter <= 0xDFFFF) {
					symbol = UnicodeTerminalSymbols.supplementaryUnassigned;
				} else if (currentCharacter >= 0xE0000 && currentCharacter <= 0xEFFFF) {
					symbol = UnicodeTerminalSymbols.supplementarySpecialPurposePlane;
				} else if (currentCharacter >= 0xF0000 && currentCharacter <= 0x10FFFF) {
					symbol = UnicodeTerminalSymbols.supplementaryPrivateUsePlanes;
				} else {
					symbol = null;
				}
			} break;
			}
			
			try {
				final int r = in.read();
				if (r == -1) {
					currentCharacter = -1;
					return symbol;
				}
				final char ch = (char) r;
				final int codePoint;
				if (Character.isHighSurrogate(ch)) {
					final char chHigh = ch;
					final int rr = in.read();
					if (rr == -1) {
						throw new NoSuchElementException();
					}
					final char chLow = (char) rr;
					codePoint = Character.toCodePoint(chHigh, chLow);
				} else {
					codePoint = ch;
				}
				currentCharacter = codePoint;
				return symbol;
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
