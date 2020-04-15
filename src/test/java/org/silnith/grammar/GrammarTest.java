package org.silnith.grammar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;


public class GrammarTest {

//    @Test
//    public void testGetProductionRemainder() {
//        final Grammar grammar = new Grammar();
//
//        final NonTerminal left = new NonTerminal("lhs");
//        final NonTerminal a = new NonTerminal("a");
//        final NonTerminal b = new NonTerminal("b");
//        final Production production = new Production(a, b);
//        final Terminal x = new CharacterLiteral('x');
//        final LookaheadItem<Terminal> item = new LookaheadItem<>(left,
//                production, 2, x);
//
//        grammar.addProduction(a, x);
//        grammar.addProduction(b, x);
//        grammar.addProduction(left, a, b);
//
//        assertEquals(Collections.singletonList(x),
//                grammar.getProductionRemainder(item));
//    }
    
    @Test
    public void testGrammar3point1() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch semicolon = new CharacterLiteral(";", ';');
        final TerminalSymbolMatch leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbolMatch rightParenthesis = new CharacterLiteral(")", ')');
        final TerminalSymbolMatch plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbolMatch comma = new CharacterLiteral(",", ',');
        final TerminalSymbolMatch id = new Identifier("id");
        final TerminalSymbolMatch num = new Identifier("num");
        final TerminalSymbolMatch assign = new Identifier(":=");
        final TerminalSymbolMatch print = new Identifier("print");
        final TerminalSymbolMatch eof = new Identifier("$");
        
        final NonTerminalSymbolMatch sp = grammar.getNonTerminalSymbol("S'");
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbolMatch l = grammar.getNonTerminalSymbol("L");
        
        grammar.addProduction(sp, new TestProductionHandler("S'"), s, eof);
        grammar.addProduction(s, new TestProductionHandler("S"), s, semicolon, s);
        grammar.addProduction(s, new TestProductionHandler("S"), id, assign, e);
        grammar.addProduction(s, new TestProductionHandler("S"), print, leftParenthesis, l, rightParenthesis);
        grammar.addProduction(e, new TestProductionHandler("E"), id);
        grammar.addProduction(e, new TestProductionHandler("E"), num);
        grammar.addProduction(e, new TestProductionHandler("E"), e, plusSign, e);
        grammar.addProduction(e, new TestProductionHandler("E"), leftParenthesis, s, comma, e, rightParenthesis);
        grammar.addProduction(l, new TestProductionHandler("L"), e);
        grammar.addProduction(l, new TestProductionHandler("L"), l, comma, e);
        
        grammar.compute();
        
        // id:=num;id:=id+(id:=num+num,id)
        // a := 7; \n b := c + (d := 5 + 6, d)
//        assertTrue(true);
//        grammar.createParser(sp, eof);
    }
    
    @Test
    public void testGrammar3point8() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbolMatch hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbolMatch star = new CharacterLiteral("*", '*');
        final TerminalSymbolMatch solidus = new CharacterLiteral("/", '/');
        final TerminalSymbolMatch leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbolMatch rightParenthesis = new CharacterLiteral(")", ')');
        
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbolMatch t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbolMatch f = grammar.getNonTerminalSymbol("F");
        
        grammar.addProduction(e, new TestProductionHandler("E"), e, plusSign, t);
        grammar.addProduction(e, new TestProductionHandler("E"), e, hyphenMinus, t);
        grammar.addProduction(e, new TestProductionHandler("E"), t);
        grammar.addProduction(t, new TestProductionHandler("T"), t, star, f);
        grammar.addProduction(t, new TestProductionHandler("T"), t, solidus, f);
        grammar.addProduction(t, new TestProductionHandler("T"), f);
        grammar.addProduction(f, new TestProductionHandler("F"), new Identifier("id"));
        grammar.addProduction(f, new TestProductionHandler("F"), new Identifier("num"));
        grammar.addProduction(f, new TestProductionHandler("F"), leftParenthesis, e, rightParenthesis);
        
        grammar.compute();
        
        assertTrue(true);
    }
    
    @Test
    public void testGrammar3point10() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbolMatch hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbolMatch star = new CharacterLiteral("*", '*');
        final TerminalSymbolMatch solidus = new CharacterLiteral("/", '/');
        final TerminalSymbolMatch leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbolMatch rightParenthesis = new CharacterLiteral(")", ')');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbolMatch t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbolMatch f = grammar.getNonTerminalSymbol("F");
        
        grammar.addProduction(s, new TestProductionHandler("S"), e, new CharacterLiteral('$'));
        grammar.addProduction(e, new TestProductionHandler("E"), e, plusSign, t);
        grammar.addProduction(e, new TestProductionHandler("E"), e, hyphenMinus, t);
        grammar.addProduction(e, new TestProductionHandler("E"), t);
        grammar.addProduction(t, new TestProductionHandler("T"), t, star, f);
        grammar.addProduction(t, new TestProductionHandler("T"), t, solidus, f);
        grammar.addProduction(t, new TestProductionHandler("T"), f);
        grammar.addProduction(f, new TestProductionHandler("F"), new Identifier("id"));
        grammar.addProduction(f, new TestProductionHandler("F"), new Identifier("num"));
        grammar.addProduction(f, new TestProductionHandler("F"), leftParenthesis, e, rightParenthesis);
        
        grammar.compute();
        
        assertTrue(true);
    }
    
    @Test
    public void testGrammar3point12() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch a = new CharacterLiteral('a');
        final TerminalSymbolMatch c = new CharacterLiteral('c');
        final TerminalSymbolMatch d = new CharacterLiteral('d');
        
        final NonTerminalSymbolMatch z = grammar.getNonTerminalSymbol("Z");
        final NonTerminalSymbolMatch y = grammar.getNonTerminalSymbol("Y");
        final NonTerminalSymbolMatch x = grammar.getNonTerminalSymbol("X");
        
        grammar.addProduction(z, new TestProductionHandler("Z"), d);
        grammar.addProduction(z, new TestProductionHandler("Z"), x, y, z);
        grammar.addProduction(y, new TestProductionHandler("Y"), new SymbolMatch[0]);
        grammar.addProduction(y, new TestProductionHandler("Y"), c);
        grammar.addProduction(x, new TestProductionHandler("X"), y);
        grammar.addProduction(x, new TestProductionHandler("X"), a);
        
        grammar.compute();
        
        final Set<NonTerminalSymbolMatch> expectedNullable = new HashSet<>(Arrays.asList(x, y));
        assertEquals(expectedNullable, grammar.getNullableSet());
        
        final Map<SymbolMatch, Set<TerminalSymbolMatch>> expectedFirst = new HashMap<>();
        expectedFirst.put(a, Collections.<TerminalSymbolMatch> singleton(a));
        expectedFirst.put(c, Collections.<TerminalSymbolMatch> singleton(c));
        expectedFirst.put(d, Collections.<TerminalSymbolMatch> singleton(d));
        expectedFirst.put(x, new HashSet<>(Arrays.asList(a, c)));
        expectedFirst.put(y, new HashSet<>(Arrays.asList(c)));
        expectedFirst.put(z, new HashSet<>(Arrays.asList(a, c, d)));
        assertEquals(expectedFirst, grammar.getFirstSet());
//        assertEquals(expectedFirst.get(x), grammar.getFirstSet(x));
//        assertEquals(expectedFirst.get(y), grammar.getFirstSet(y));
//        assertEquals(expectedFirst.get(z), grammar.getFirstSet(z));
        
        final Map<SymbolMatch, Set<TerminalSymbolMatch>> expectedFollow = new HashMap<>();
        expectedFollow.put(x, new HashSet<>(Arrays.asList(a, c, d)));
        expectedFollow.put(y, new HashSet<>(Arrays.asList(a, c, d)));
        expectedFollow.put(z, Collections.<TerminalSymbolMatch> emptySet());
        assertEquals(expectedFollow.get(x), grammar.getFollowSet(x));
        assertEquals(expectedFollow.get(y), grammar.getFollowSet(y));
        assertEquals(expectedFollow.get(z), grammar.getFollowSet(z));
    }
    
    @Test
    public void testGrammar3point15() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbolMatch hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbolMatch star = new CharacterLiteral("*", '*');
        final TerminalSymbolMatch solidus = new CharacterLiteral("/", '/');
        final TerminalSymbolMatch leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbolMatch rightParenthesis = new CharacterLiteral(")", ')');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        final TerminalSymbolMatch id = new Identifier("id");
        final TerminalSymbolMatch num = new Identifier("num");
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbolMatch ep = grammar.getNonTerminalSymbol("E'");
        final NonTerminalSymbolMatch t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbolMatch tp = grammar.getNonTerminalSymbol("T'");
        final NonTerminalSymbolMatch f = grammar.getNonTerminalSymbol("F");
        
        grammar.addProduction(s, new TestProductionHandler("S"), e, eof);
        grammar.addProduction(e, new TestProductionHandler("E"), t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), plusSign, t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), hyphenMinus, t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), new SymbolMatch[0]);
        grammar.addProduction(t, new TestProductionHandler("T"), f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), star, f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), solidus, f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), new SymbolMatch[0]);
        grammar.addProduction(f, new TestProductionHandler("F"), id);
        grammar.addProduction(f, new TestProductionHandler("F"), num);
        grammar.addProduction(f, new TestProductionHandler("F"), leftParenthesis, e, rightParenthesis);
        
        grammar.compute();
        
        final Set<NonTerminalSymbolMatch> expectedNullable = new HashSet<>(Arrays.asList(ep, tp));
        assertEquals(expectedNullable, grammar.getNullableSet());
        final Map<SymbolMatch, Set<TerminalSymbolMatch>> expectedFirst = new HashMap<>();
        expectedFirst.put(plusSign, Collections.<TerminalSymbolMatch> singleton(plusSign));
        expectedFirst.put(hyphenMinus, Collections.<TerminalSymbolMatch> singleton(hyphenMinus));
        expectedFirst.put(star, Collections.<TerminalSymbolMatch> singleton(star));
        expectedFirst.put(solidus, Collections.<TerminalSymbolMatch> singleton(solidus));
        expectedFirst.put(leftParenthesis, Collections.<TerminalSymbolMatch> singleton(leftParenthesis));
        expectedFirst.put(rightParenthesis, Collections.<TerminalSymbolMatch> singleton(rightParenthesis));
        expectedFirst.put(eof, Collections.<TerminalSymbolMatch> singleton(eof));
        expectedFirst.put(id, Collections.<TerminalSymbolMatch> singleton(id));
        expectedFirst.put(num, Collections.<TerminalSymbolMatch> singleton(num));
        expectedFirst.put(s, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(e, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(ep, new HashSet<>(Arrays.asList(plusSign, hyphenMinus)));
        expectedFirst.put(t, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(tp, new HashSet<>(Arrays.asList(star, solidus)));
        expectedFirst.put(f, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        assertEquals(expectedFirst, grammar.getFirstSet());
        final Map<SymbolMatch, Set<TerminalSymbolMatch>> expectedFollow = new HashMap<>();
        expectedFollow.put(s, Collections.<TerminalSymbolMatch> emptySet());
        expectedFollow.put(e, new HashSet<>(Arrays.asList(rightParenthesis, eof)));
        expectedFollow.put(ep, new HashSet<>(Arrays.asList(rightParenthesis, eof)));
        expectedFollow.put(t, new HashSet<>(Arrays.asList(rightParenthesis, plusSign, hyphenMinus, eof)));
        expectedFollow.put(tp, new HashSet<>(Arrays.asList(rightParenthesis, plusSign, hyphenMinus, eof)));
        expectedFollow.put(f,
                new HashSet<>(Arrays.asList(rightParenthesis, star, solidus, plusSign, hyphenMinus, eof)));
//        assertEquals(expectedFollow, grammar.getFollowSet());
//        assertEquals(expectedFollow.get(num), grammar.getFollowSet(num));
        assertEquals(expectedFollow.get(s), grammar.getFollowSet(s));
        assertEquals(expectedFollow.get(e), grammar.getFollowSet(e));
        assertEquals(expectedFollow.get(ep), grammar.getFollowSet(ep));
        assertEquals(expectedFollow.get(t), grammar.getFollowSet(t));
        assertEquals(expectedFollow.get(tp), grammar.getFollowSet(tp));
        assertEquals(expectedFollow.get(f), grammar.getFollowSet(f));
    }
    
//    @Test
    public void testGrammar3point20() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch x = new CharacterLiteral('x');
        final TerminalSymbolMatch leftParen = new CharacterLiteral('(');
        final TerminalSymbolMatch rightParen = new CharacterLiteral(')');
        final TerminalSymbolMatch comma = new CharacterLiteral(',');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch sp = grammar.getNonTerminalSymbol("S'");
        final NonTerminalSymbolMatch l = grammar.getNonTerminalSymbol("L");
        
        grammar.addProduction(sp, new TestProductionHandler("S'"), s, eof);
        grammar.addProduction(s, new TestProductionHandler("S"), leftParen, l, rightParen);
        grammar.addProduction(s, new TestProductionHandler("S"), x);
        grammar.addProduction(l, new TestProductionHandler("L"), s);
        grammar.addProduction(l, new TestProductionHandler("L"), l, comma, s);
        
        grammar.compute();
        
//        final Set<ItemSet> parserStates = grammar.createParser(sp);
//        for (final ItemSet state : parserStates) {
//            System.out.println(state);
//        }

//        final Set<ItemSet> expectedStates = new HashSet<>();
//        final ItemSet expectedState1 = new ItemSet();
//        expectedState1.add(new Item(sp, new Production(s, eof), 0));
//        expectedState1.add(new Item(s, new Production(leftParen, l, rightParen), 0));
//        expectedState1.add(new Item(s, new Production(x), 0));
//        expectedStates.add(expectedState1);
//        final ItemSet expectedState2 = new ItemSet();
//        expectedState2.add(new Item(s, new Production(x), 1));
//        expectedStates.add(expectedState2);
//        final ItemSet expectedState3 = new ItemSet();
//        expectedState3.add(new Item(s, new Production(leftParen, l, rightParen), 1));
//        expectedState3.add(new Item(l, new Production(s), 0));
//        expectedState3.add(new Item(l, new Production(l, comma, s), 0));
//        expectedState3.add(new Item(s, new Production(leftParen, l, rightParen), 0));
//        expectedState3.add(new Item(s, new Production(x), 0));
//        expectedStates.add(expectedState3);
//        final ItemSet expectedState4 = new ItemSet();
//        expectedState4.add(new Item(sp, new Production(s, eof), 1));
//        expectedStates.add(expectedState4);
//        final ItemSet expectedState5 = new ItemSet();
//        expectedState5.add(new Item(s, new Production(leftParen, l, rightParen), 2));
//        expectedState5.add(new Item(l, new Production(l, comma, s), 1));
//        expectedStates.add(expectedState5);
//        final ItemSet expectedState6 = new ItemSet();
//        expectedState6.add(new Item(s, new Production(leftParen, l, rightParen), 3));
//        expectedStates.add(expectedState6);
//        final ItemSet expectedState7 = new ItemSet();
//        expectedState7.add(new Item(l, new Production(s), 1));
//        expectedStates.add(expectedState7);
//        final ItemSet expectedState8 = new ItemSet();
//        expectedState8.add(new Item(l, new Production(l, comma, s), 2));
//        expectedState8.add(new Item(s, new Production(leftParen, l, rightParen), 0));
//        expectedState8.add(new Item(s, new Production(x), 0));
//        expectedStates.add(expectedState8);
//        final ItemSet expectedState9 = new ItemSet();
//        expectedState9.add(new Item(l, new Production(l, comma, s), 3));
//        expectedStates.add(expectedState9);
//        for (final ItemSet state : expectedStates) {
//            System.out.println(state);
//        }

//        assertEquals(expectedStates, parserStates);
    }
    
//    @Test
    public void testGrammar3point23() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch x = new CharacterLiteral('x');
        final TerminalSymbolMatch plus = new CharacterLiteral('+');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbolMatch t = grammar.getNonTerminalSymbol("T");
        
        grammar.addProduction(s, new TestProductionHandler("S"), e, eof);
        grammar.addProduction(e, new TestProductionHandler("E"), t, plus, e);
        grammar.addProduction(e, new TestProductionHandler("E"), t);
        grammar.addProduction(t, new TestProductionHandler("T"), x);
        
        grammar.compute();
        
//        final Set<ItemSet> parserStates = grammar.createParser(s);
//        for (final ItemSet state : parserStates) {
//            System.out.println(state);
//        }

//        final Set<ItemSet> expectedStates = new HashSet<>();
//        final ItemSet expectedState1 = new ItemSet();
//        expectedState1.add(new Item(s, new Production(e, eof), 0));
//        expectedState1.add(new Item(e, new Production(t, plus, e), 0));
//        expectedState1.add(new Item(e, new Production(t), 0));
//        expectedState1.add(new Item(t, new Production(x), 0));
//        expectedStates.add(expectedState1);
//        final ItemSet expectedState2 = new ItemSet();
//        expectedState2.add(new Item(s, new Production(e, eof), 1));
//        expectedStates.add(expectedState2);
//        final ItemSet expectedState3 = new ItemSet();
//        expectedState3.add(new Item(e, new Production(t, plus, e), 1));
//        expectedState3.add(new Item(e, new Production(t), 1));
//        expectedStates.add(expectedState3);
//        final ItemSet expectedState4 = new ItemSet();
//        expectedState4.add(new Item(e, new Production(t, plus, e), 2));
//        expectedState4.add(new Item(e, new Production(t, plus, e), 0));
//        expectedState4.add(new Item(e, new Production(t), 0));
//        expectedState4.add(new Item(t, new Production(x), 0));
//        expectedStates.add(expectedState4);
//        final ItemSet expectedState5 = new ItemSet();
//        expectedState5.add(new Item(t, new Production(x), 1));
//        expectedStates.add(expectedState5);
//        final ItemSet expectedState6 = new ItemSet();
//        expectedState6.add(new Item(e, new Production(t, plus, e), 3));
//        expectedStates.add(expectedState6);
//        for (final ItemSet state : expectedStates) {
//            System.out.println(state);
//        }

//        assertEquals(expectedStates, parserStates);
    }
    
    @Test
    public void testLeftRecursion() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch space = new CharacterLiteral(' ');
        final TerminalSymbolMatch x = new CharacterLiteral('x');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbolMatch white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbolMatch white_Kleene = grammar.getNonTerminalSymbol("white*");
        final NonTerminalSymbolMatch end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Kleene, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), new SymbolMatch[0]);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), white_Kleene, white);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        
//        System.out.println(grammar.getFollowSet(end));
//        System.out.println(grammar.getFollowSet(white_Kleene));
//        System.out.println(grammar.getFollowSet(white));
        
        grammar.createParser(s, eof);
    }
    
    @Test
    public void testRightRecursion() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch space = new CharacterLiteral(' ');
        final TerminalSymbolMatch x = new CharacterLiteral('x');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbolMatch white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbolMatch white_Kleene = grammar.getNonTerminalSymbol("white*");
        final NonTerminalSymbolMatch end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Kleene, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), new SymbolMatch[0]);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), white, white_Kleene);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        
//        System.out.println(grammar.getFollowSet(end));
//        System.out.println(grammar.getFollowSet(white_Kleene));
//        System.out.println(grammar.getFollowSet(white));
        
        grammar.createParser(s, eof);
    }
    
    @Test
    public void testFixFollowSets() {
        final Grammar grammar = new Grammar();
        
        final TerminalSymbolMatch space = new CharacterLiteral(' ');
        final TerminalSymbolMatch x = new CharacterLiteral('x');
        final TerminalSymbolMatch eof = new CharacterLiteral('$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbolMatch white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbolMatch white_Plus = grammar.getNonTerminalSymbol("white+");
        final NonTerminalSymbolMatch end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, end);
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Plus, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Plus, new TestProductionHandler("white+"), white);
        grammar.addProduction(white_Plus, new TestProductionHandler("white+"), white, white_Plus);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        
        System.out.println(grammar.getFollowSet(end));
        System.out.println(grammar.getFollowSet(white_Plus));
        System.out.println(grammar.getFollowSet(white));
        
        grammar.createParser(s, eof);
    }
    
}
