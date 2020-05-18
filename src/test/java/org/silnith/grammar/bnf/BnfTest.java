package org.silnith.grammar.bnf;

import static org.silnith.grammar.bnf.AsciiTerminal.A;
import static org.silnith.grammar.bnf.AsciiTerminal.B;
import static org.silnith.grammar.bnf.AsciiTerminal.C;
import static org.silnith.grammar.bnf.AsciiTerminal.D;
import static org.silnith.grammar.bnf.AsciiTerminal.E;
import static org.silnith.grammar.bnf.AsciiTerminal.F;
import static org.silnith.grammar.bnf.AsciiTerminal.G;
import static org.silnith.grammar.bnf.AsciiTerminal.H;
import static org.silnith.grammar.bnf.AsciiTerminal.I;
import static org.silnith.grammar.bnf.AsciiTerminal.J;
import static org.silnith.grammar.bnf.AsciiTerminal.K;
import static org.silnith.grammar.bnf.AsciiTerminal.L;
import static org.silnith.grammar.bnf.AsciiTerminal.M;
import static org.silnith.grammar.bnf.AsciiTerminal.N;
import static org.silnith.grammar.bnf.AsciiTerminal.O;
import static org.silnith.grammar.bnf.AsciiTerminal.P;
import static org.silnith.grammar.bnf.AsciiTerminal.Q;
import static org.silnith.grammar.bnf.AsciiTerminal.R;
import static org.silnith.grammar.bnf.AsciiTerminal.S;
import static org.silnith.grammar.bnf.AsciiTerminal.T;
import static org.silnith.grammar.bnf.AsciiTerminal.U;
import static org.silnith.grammar.bnf.AsciiTerminal.V;
import static org.silnith.grammar.bnf.AsciiTerminal.W;
import static org.silnith.grammar.bnf.AsciiTerminal.X;
import static org.silnith.grammar.bnf.AsciiTerminal.Y;
import static org.silnith.grammar.bnf.AsciiTerminal.Z;
import static org.silnith.grammar.bnf.AsciiTerminal.a;
import static org.silnith.grammar.bnf.AsciiTerminal.and;
import static org.silnith.grammar.bnf.AsciiTerminal.apos;
import static org.silnith.grammar.bnf.AsciiTerminal.at;
import static org.silnith.grammar.bnf.AsciiTerminal.b;
import static org.silnith.grammar.bnf.AsciiTerminal.backslash;
import static org.silnith.grammar.bnf.AsciiTerminal.backtick;
import static org.silnith.grammar.bnf.AsciiTerminal.bang;
import static org.silnith.grammar.bnf.AsciiTerminal.bar;
import static org.silnith.grammar.bnf.AsciiTerminal.c;
import static org.silnith.grammar.bnf.AsciiTerminal.colon;
import static org.silnith.grammar.bnf.AsciiTerminal.comma;
import static org.silnith.grammar.bnf.AsciiTerminal.d;
import static org.silnith.grammar.bnf.AsciiTerminal.dash;
import static org.silnith.grammar.bnf.AsciiTerminal.dollar;
import static org.silnith.grammar.bnf.AsciiTerminal.dot;
import static org.silnith.grammar.bnf.AsciiTerminal.e;
import static org.silnith.grammar.bnf.AsciiTerminal.eight;
import static org.silnith.grammar.bnf.AsciiTerminal.eol;
import static org.silnith.grammar.bnf.AsciiTerminal.eq;
import static org.silnith.grammar.bnf.AsciiTerminal.f;
import static org.silnith.grammar.bnf.AsciiTerminal.five;
import static org.silnith.grammar.bnf.AsciiTerminal.four;
import static org.silnith.grammar.bnf.AsciiTerminal.g;
import static org.silnith.grammar.bnf.AsciiTerminal.gt;
import static org.silnith.grammar.bnf.AsciiTerminal.h;
import static org.silnith.grammar.bnf.AsciiTerminal.hash;
import static org.silnith.grammar.bnf.AsciiTerminal.hat;
import static org.silnith.grammar.bnf.AsciiTerminal.i;
import static org.silnith.grammar.bnf.AsciiTerminal.j;
import static org.silnith.grammar.bnf.AsciiTerminal.k;
import static org.silnith.grammar.bnf.AsciiTerminal.l;
import static org.silnith.grammar.bnf.AsciiTerminal.leftbrace;
import static org.silnith.grammar.bnf.AsciiTerminal.leftbracket;
import static org.silnith.grammar.bnf.AsciiTerminal.leftparen;
import static org.silnith.grammar.bnf.AsciiTerminal.lt;
import static org.silnith.grammar.bnf.AsciiTerminal.m;
import static org.silnith.grammar.bnf.AsciiTerminal.n;
import static org.silnith.grammar.bnf.AsciiTerminal.nine;
import static org.silnith.grammar.bnf.AsciiTerminal.o;
import static org.silnith.grammar.bnf.AsciiTerminal.one;
import static org.silnith.grammar.bnf.AsciiTerminal.p;
import static org.silnith.grammar.bnf.AsciiTerminal.percent;
import static org.silnith.grammar.bnf.AsciiTerminal.plus;
import static org.silnith.grammar.bnf.AsciiTerminal.q;
import static org.silnith.grammar.bnf.AsciiTerminal.question;
import static org.silnith.grammar.bnf.AsciiTerminal.quote;
import static org.silnith.grammar.bnf.AsciiTerminal.r;
import static org.silnith.grammar.bnf.AsciiTerminal.rightbrace;
import static org.silnith.grammar.bnf.AsciiTerminal.rightbracket;
import static org.silnith.grammar.bnf.AsciiTerminal.rightparen;
import static org.silnith.grammar.bnf.AsciiTerminal.s;
import static org.silnith.grammar.bnf.AsciiTerminal.semi;
import static org.silnith.grammar.bnf.AsciiTerminal.seven;
import static org.silnith.grammar.bnf.AsciiTerminal.six;
import static org.silnith.grammar.bnf.AsciiTerminal.slash;
import static org.silnith.grammar.bnf.AsciiTerminal.space;
import static org.silnith.grammar.bnf.AsciiTerminal.star;
import static org.silnith.grammar.bnf.AsciiTerminal.t;
import static org.silnith.grammar.bnf.AsciiTerminal.three;
import static org.silnith.grammar.bnf.AsciiTerminal.tilde;
import static org.silnith.grammar.bnf.AsciiTerminal.two;
import static org.silnith.grammar.bnf.AsciiTerminal.u;
import static org.silnith.grammar.bnf.AsciiTerminal.underscope;
import static org.silnith.grammar.bnf.AsciiTerminal.v;
import static org.silnith.grammar.bnf.AsciiTerminal.w;
import static org.silnith.grammar.bnf.AsciiTerminal.x;
import static org.silnith.grammar.bnf.AsciiTerminal.y;
import static org.silnith.grammar.bnf.AsciiTerminal.z;
import static org.silnith.grammar.bnf.AsciiTerminal.zero;

import org.junit.Before;
import org.junit.Test;
import org.silnith.grammar.EnumSetFactory;
import org.silnith.grammar.Grammar;
import org.silnith.grammar.NonTerminalSymbol;
import org.silnith.grammar.Parser;
import org.silnith.grammar.TestProductionHandler;

public class BnfTest {
    private Grammar<AsciiTerminal> grammar;
    private NonTerminalSymbol nt(final String n) {
        return grammar.getNonTerminalSymbol(n);
    }
    @Before
    public void setUp() {
        grammar = new Grammar<>(new EnumSetFactory<>(AsciiTerminal.class));
        
        grammar.setEndOfFileSymbol(AsciiTerminal.eof);
        
        grammar.addProduction(nt("syntax"), new TestProductionHandler("syntax"), nt("rule"));
        grammar.addProduction(nt("syntax"), new TestProductionHandler("syntax"), nt("rule"), nt("syntax"));
        
        grammar.addProduction(nt("rule"), new TestProductionHandler("rule"), nt("opt-whitespace"), lt, nt("rule-name"), gt, nt("opt-whitespace"), colon, colon, eq, nt("opt-whitespace"), nt("expression"), nt("line-end"));
        
        grammar.addProduction(nt("opt-whitespace"), new TestProductionHandler("opt-whitespace"), space, nt("opt-whitespace"));
        grammar.addProduction(nt("opt-whitespace"), new TestProductionHandler("opt-whitespace"));
        
        grammar.addProduction(nt("expression"), new TestProductionHandler("expression"), nt("list"));
        grammar.addProduction(nt("expression"), new TestProductionHandler("expression"), nt("list"), nt("opt-whitespace"), bar, nt("opt-whitespace"), nt("expression"));
        
        grammar.addProduction(nt("line-end"), new TestProductionHandler("line-end"), nt("opt-whitespace"), eol);
        grammar.addProduction(nt("line-end"), new TestProductionHandler("line-end"), nt("line-end"), nt("line-end"));
        
        grammar.addProduction(nt("list"), new TestProductionHandler("list"), nt("term"));
        grammar.addProduction(nt("list"), new TestProductionHandler("list"), nt("term"), nt("opt-whitespace"), nt("list"));
        
        grammar.addProduction(nt("term"), new TestProductionHandler("term"), nt("literal"));
        grammar.addProduction(nt("term"), new TestProductionHandler("term"), lt, nt("rule-name"), gt);
        
        grammar.addProduction(nt("literal"), new TestProductionHandler("literal"), quote, nt("text1"), quote);
        grammar.addProduction(nt("literal"), new TestProductionHandler("literal"), apos, nt("text2"), apos);
        
        grammar.addProduction(nt("text1"), new TestProductionHandler("text1"), nt("character1"), nt("text1"));
        grammar.addProduction(nt("text1"), new TestProductionHandler("text1"));
        
        grammar.addProduction(nt("text2"), new TestProductionHandler("text2"), nt("character2"), nt("text2"));
        grammar.addProduction(nt("text2"), new TestProductionHandler("text2"));
        
        grammar.addProduction(nt("character"), new TestProductionHandler("character"), nt("letter"));
        grammar.addProduction(nt("character"), new TestProductionHandler("character"), nt("digit"));
        grammar.addProduction(nt("character"), new TestProductionHandler("character"), nt("symbol"));
        
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), A);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), B);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), C);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), D);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), E);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), F);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), G);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), H);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), I);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), J);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), K);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), L);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), M);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), N);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), O);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), P);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), Q);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), R);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), S);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), T);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), U);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), V);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), W);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), X);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), Y);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), Z);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), a);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), b);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), c);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), d);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), e);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), f);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), g);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), h);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), i);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), j);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), k);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), l);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), m);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), n);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), o);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), p);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), q);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), r);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), s);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), t);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), u);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), v);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), w);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), x);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), y);
        grammar.addProduction(nt("letter"), new TestProductionHandler("letter"), z);
        
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), zero);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), one);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), two);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), three);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), four);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), five);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), six);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), seven);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), eight);
        grammar.addProduction(nt("digit"), new TestProductionHandler("digit"), nine);
        
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), bar);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), space);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), bang);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), hash);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), dollar);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), percent);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), and);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), leftparen);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), rightparen);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), star);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), plus);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), comma);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), dash);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), dot);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), slash);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), colon);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), semi);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), gt);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), eq);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), lt);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), question);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), at);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), leftbracket);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), backslash);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), rightbracket);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), hat);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), underscope);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), backtick);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), leftbrace);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), rightbrace);
        grammar.addProduction(nt("symbol"), new TestProductionHandler("symbol"), tilde);
        
        grammar.addProduction(nt("character1"), new TestProductionHandler("character1"), nt("character"));
        grammar.addProduction(nt("character1"), new TestProductionHandler("character1"), apos);
        
        grammar.addProduction(nt("character2"), new TestProductionHandler("character2"), nt("character"));
        grammar.addProduction(nt("character2"), new TestProductionHandler("character2"), quote);
        
        grammar.addProduction(nt("rule-name"), new TestProductionHandler("rule-name"), nt("letter"));
        grammar.addProduction(nt("rule-name"), new TestProductionHandler("rule-name"), nt("rule-name"), nt("rule-char"));
        
        grammar.addProduction(nt("rule-char"), new TestProductionHandler("rule-char"), nt("letter"));
        grammar.addProduction(nt("rule-char"), new TestProductionHandler("rule-char"), nt("digit"));
        grammar.addProduction(nt("rule-char"), new TestProductionHandler("rule-char"), dash);
    }
    @Test
    public void testParserLiteral() {
        grammar.setStartSymbol(nt("literal"));
        final Parser<AsciiTerminal> parser = grammar.createParser();
    }
    @Test
    public void testParserTerm() {
        grammar.setStartSymbol(nt("term"));
        final Parser<AsciiTerminal> parser = grammar.createParser();
    }
    @Test
    public void testParserList() {
        grammar.setStartSymbol(nt("list"));
        final Parser<AsciiTerminal> parser = grammar.createParser();
    }
    @Test
    public void testParserExpression() {
        grammar.setStartSymbol(nt("expression"));
        final Parser<AsciiTerminal> parser = grammar.createParser();
    }
    @Test
    public void testParserLineEnd() {
        grammar.setStartSymbol(nt("line-end"));
        final Parser<AsciiTerminal> parser = grammar.createParser();
    }
}
