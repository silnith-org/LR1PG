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
//        final Grammar<CharacterLiteral> grammar = new Grammar<>();
//
//        final NonTerminal left = new NonTerminal("lhs");
//        final NonTerminal a = new NonTerminal("a");
//        final NonTerminal b = new NonTerminal("b");
//        final Production production = new Production(a, b);
//        final CharacterLiteral x = new CharacterLiteral('x');
//        final LookaheadItem<CharacterLiteral> item = new LookaheadItem<>(left,
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol semicolon = new CharacterLiteral(";", ';');
        final TerminalSymbol leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbol rightParenthesis = new CharacterLiteral(")", ')');
        final TerminalSymbol plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbol comma = new CharacterLiteral(",", ',');
        final TerminalSymbol id = new Identifier("id");
        final TerminalSymbol num = new Identifier("num");
        final TerminalSymbol assign = new Identifier(":=");
        final TerminalSymbol print = new Identifier("print");
        final TerminalSymbol eof = new Identifier("$");
        
        final NonTerminalSymbol sp = grammar.getNonTerminalSymbol("S'");
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol l = grammar.getNonTerminalSymbol("L");
        
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbol hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbol star = new CharacterLiteral("*", '*');
        final TerminalSymbol solidus = new CharacterLiteral("/", '/');
        final TerminalSymbol leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbol rightParenthesis = new CharacterLiteral(")", ')');
        
        final NonTerminalSymbol e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbol f = grammar.getNonTerminalSymbol("F");
        
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbol hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbol star = new CharacterLiteral("*", '*');
        final TerminalSymbol solidus = new CharacterLiteral("/", '/');
        final TerminalSymbol leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbol rightParenthesis = new CharacterLiteral(")", ')');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbol f = grammar.getNonTerminalSymbol("F");
        
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol a = new CharacterLiteral('a');
        final TerminalSymbol c = new CharacterLiteral('c');
        final TerminalSymbol d = new CharacterLiteral('d');
        
        final NonTerminalSymbol z = grammar.getNonTerminalSymbol("Z");
        final NonTerminalSymbol y = grammar.getNonTerminalSymbol("Y");
        final NonTerminalSymbol x = grammar.getNonTerminalSymbol("X");
        
        grammar.addProduction(z, new TestProductionHandler("Z"), d);
        grammar.addProduction(z, new TestProductionHandler("Z"), x, y, z);
        grammar.addProduction(y, new TestProductionHandler("Y"), new Symbol[0]);
        grammar.addProduction(y, new TestProductionHandler("Y"), c);
        grammar.addProduction(x, new TestProductionHandler("X"), y);
        grammar.addProduction(x, new TestProductionHandler("X"), a);
        
        grammar.compute();
        
        final Set<NonTerminalSymbol> expectedNullable = new HashSet<>(Arrays.asList(x, y));
        assertEquals(expectedNullable, grammar.getNullableSet());
        
        final Map<Symbol, Set<TerminalSymbol>> expectedFirst = new HashMap<>();
        expectedFirst.put(a, Collections.<TerminalSymbol> singleton(a));
        expectedFirst.put(c, Collections.<TerminalSymbol> singleton(c));
        expectedFirst.put(d, Collections.<TerminalSymbol> singleton(d));
        expectedFirst.put(x, new HashSet<>(Arrays.asList(a, c)));
        expectedFirst.put(y, new HashSet<>(Arrays.asList(c)));
        expectedFirst.put(z, new HashSet<>(Arrays.asList(a, c, d)));
        assertEquals(expectedFirst, grammar.getFirstSet());
//        assertEquals(expectedFirst.get(x), grammar.getFirstSet(x));
//        assertEquals(expectedFirst.get(y), grammar.getFirstSet(y));
//        assertEquals(expectedFirst.get(z), grammar.getFirstSet(z));
        
        final Map<Symbol, Set<TerminalSymbol>> expectedFollow = new HashMap<>();
        expectedFollow.put(x, new HashSet<>(Arrays.asList(a, c, d)));
        expectedFollow.put(y, new HashSet<>(Arrays.asList(a, c, d)));
        expectedFollow.put(z, Collections.<TerminalSymbol> emptySet());
        assertEquals(expectedFollow.get(x), grammar.getFollowSet().get(x));
        assertEquals(expectedFollow.get(y), grammar.getFollowSet().get(y));
        assertEquals(expectedFollow.get(z), grammar.getFollowSet().get(z));
    }
    
    @Test
    public void testGrammar3point15() {
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol plusSign = new CharacterLiteral("+", '+');
        final TerminalSymbol hyphenMinus = new CharacterLiteral("-", '-');
        final TerminalSymbol star = new CharacterLiteral("*", '*');
        final TerminalSymbol solidus = new CharacterLiteral("/", '/');
        final TerminalSymbol leftParenthesis = new CharacterLiteral("(", '(');
        final TerminalSymbol rightParenthesis = new CharacterLiteral(")", ')');
        final TerminalSymbol eof = new CharacterLiteral('$');
        final TerminalSymbol id = new Identifier("id");
        final TerminalSymbol num = new Identifier("num");
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol ep = grammar.getNonTerminalSymbol("E'");
        final NonTerminalSymbol t = grammar.getNonTerminalSymbol("T");
        final NonTerminalSymbol tp = grammar.getNonTerminalSymbol("T'");
        final NonTerminalSymbol f = grammar.getNonTerminalSymbol("F");
        
        grammar.addProduction(s, new TestProductionHandler("S"), e, eof);
        grammar.addProduction(e, new TestProductionHandler("E"), t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), plusSign, t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), hyphenMinus, t, ep);
        grammar.addProduction(ep, new TestProductionHandler("E'"), new Symbol[0]);
        grammar.addProduction(t, new TestProductionHandler("T"), f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), star, f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), solidus, f, tp);
        grammar.addProduction(tp, new TestProductionHandler("T'"), new Symbol[0]);
        grammar.addProduction(f, new TestProductionHandler("F"), id);
        grammar.addProduction(f, new TestProductionHandler("F"), num);
        grammar.addProduction(f, new TestProductionHandler("F"), leftParenthesis, e, rightParenthesis);
        
        grammar.compute();
        
        final Set<NonTerminalSymbol> expectedNullable = new HashSet<>(Arrays.asList(ep, tp));
        assertEquals(expectedNullable, grammar.getNullableSet());
        final Map<Symbol, Set<TerminalSymbol>> expectedFirst = new HashMap<>();
        expectedFirst.put(plusSign, Collections.<TerminalSymbol> singleton(plusSign));
        expectedFirst.put(hyphenMinus, Collections.<TerminalSymbol> singleton(hyphenMinus));
        expectedFirst.put(star, Collections.<TerminalSymbol> singleton(star));
        expectedFirst.put(solidus, Collections.<TerminalSymbol> singleton(solidus));
        expectedFirst.put(leftParenthesis, Collections.<TerminalSymbol> singleton(leftParenthesis));
        expectedFirst.put(rightParenthesis, Collections.<TerminalSymbol> singleton(rightParenthesis));
        expectedFirst.put(eof, Collections.<TerminalSymbol> singleton(eof));
        expectedFirst.put(id, Collections.<TerminalSymbol> singleton(id));
        expectedFirst.put(num, Collections.<TerminalSymbol> singleton(num));
        expectedFirst.put(s, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(e, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(ep, new HashSet<>(Arrays.asList(plusSign, hyphenMinus)));
        expectedFirst.put(t, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        expectedFirst.put(tp, new HashSet<>(Arrays.asList(star, solidus)));
        expectedFirst.put(f, new HashSet<>(Arrays.asList(leftParenthesis, id, num)));
        assertEquals(expectedFirst, grammar.getFirstSet());
        final Map<Symbol, Set<TerminalSymbol>> expectedFollow = new HashMap<>();
        expectedFollow.put(s, Collections.<TerminalSymbol> emptySet());
        expectedFollow.put(e, new HashSet<>(Arrays.asList(rightParenthesis, eof)));
        expectedFollow.put(ep, new HashSet<>(Arrays.asList(rightParenthesis, eof)));
        expectedFollow.put(t, new HashSet<>(Arrays.asList(rightParenthesis, plusSign, hyphenMinus, eof)));
        expectedFollow.put(tp, new HashSet<>(Arrays.asList(rightParenthesis, plusSign, hyphenMinus, eof)));
        expectedFollow.put(f,
                new HashSet<>(Arrays.asList(rightParenthesis, star, solidus, plusSign, hyphenMinus, eof)));
//        assertEquals(expectedFollow, grammar.getFollowSet());
//        assertEquals(expectedFollow.get(num), grammar.getFollowSet(num));
        assertEquals(expectedFollow.get(s), grammar.getFollowSet().get(s));
        assertEquals(expectedFollow.get(e), grammar.getFollowSet().get(e));
        assertEquals(expectedFollow.get(ep), grammar.getFollowSet().get(ep));
        assertEquals(expectedFollow.get(t), grammar.getFollowSet().get(t));
        assertEquals(expectedFollow.get(tp), grammar.getFollowSet().get(tp));
        assertEquals(expectedFollow.get(f), grammar.getFollowSet().get(f));
    }
    
//    @Test
    public void testGrammar3point20() {
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol x = new CharacterLiteral('x');
        final TerminalSymbol leftParen = new CharacterLiteral('(');
        final TerminalSymbol rightParen = new CharacterLiteral(')');
        final TerminalSymbol comma = new CharacterLiteral(',');
        final TerminalSymbol eof = new CharacterLiteral('$');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol sp = grammar.getNonTerminalSymbol("S'");
        final NonTerminalSymbol l = grammar.getNonTerminalSymbol("L");
        
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbol x = new CharacterLiteral('x');
        final TerminalSymbol plus = new CharacterLiteral('+');
        final TerminalSymbol eof = new CharacterLiteral('$');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol e = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol t = grammar.getNonTerminalSymbol("T");
        
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
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final CharacterLiteral space = new CharacterLiteral(' ');
        final CharacterLiteral x = new CharacterLiteral('x');
        final CharacterLiteral eof = new CharacterLiteral('$');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbol white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbol white_Kleene = grammar.getNonTerminalSymbol("white*");
        final NonTerminalSymbol end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Kleene, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), new Symbol[0]);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), white_Kleene, white);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        grammar.setStartSymbol(s);
        grammar.setEndOfFileSymbol(eof);
        
//        System.out.println(grammar.getFollowSet(end));
//        System.out.println(grammar.getFollowSet(white_Kleene));
//        System.out.println(grammar.getFollowSet(white));
        
        grammar.createParser();
    }
    
    @Test
    public void testRightRecursion() {
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final CharacterLiteral space = new CharacterLiteral(' ');
        final CharacterLiteral x = new CharacterLiteral('x');
        final CharacterLiteral eof = new CharacterLiteral('$');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbol white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbol white_Kleene = grammar.getNonTerminalSymbol("white*");
        final NonTerminalSymbol end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Kleene, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), new Symbol[0]);
        grammar.addProduction(white_Kleene, new TestProductionHandler("white*"), white, white_Kleene);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        grammar.setStartSymbol(s);
        grammar.setEndOfFileSymbol(eof);
        
//        System.out.println(grammar.getFollowSet(end));
//        System.out.println(grammar.getFollowSet(white_Kleene));
//        System.out.println(grammar.getFollowSet(white));
        
        grammar.createParser();
    }
    
    @Test
    public void testFixFollowSets() {
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final CharacterLiteral space = new CharacterLiteral(' ');
        final CharacterLiteral x = new CharacterLiteral('x');
        final CharacterLiteral eof = new CharacterLiteral('$');
        
        final NonTerminalSymbol s = grammar.getNonTerminalSymbol("s");
        final NonTerminalSymbol white = grammar.getNonTerminalSymbol("white");
        final NonTerminalSymbol white_Plus = grammar.getNonTerminalSymbol("white+");
        final NonTerminalSymbol end = grammar.getNonTerminalSymbol("end");
        
        grammar.addProduction(s, new TestProductionHandler("s"), end, end);
        grammar.addProduction(s, new TestProductionHandler("s"), end, white_Plus, end);
        grammar.addProduction(end, new TestProductionHandler("end"), x);
        grammar.addProduction(white_Plus, new TestProductionHandler("white+"), white);
        grammar.addProduction(white_Plus, new TestProductionHandler("white+"), white, white_Plus);
        grammar.addProduction(white, new TestProductionHandler("white"), space);
        
        grammar.compute();
        grammar.setStartSymbol(s);
        grammar.setEndOfFileSymbol(eof);
        
//        System.out.println(grammar.getFollowSet().get(end));
//        System.out.println(grammar.getFollowSet().get(white_Plus));
//        System.out.println(grammar.getFollowSet().get(white));
        
        grammar.createParser();
    }
    
}
