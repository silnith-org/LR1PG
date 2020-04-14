package org.silnith.grammar;

import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.silnith.grammar.example.Terminals;
import org.silnith.grammar.example.ExampleGrammar;

/**
 * This constructs a parser for a grammar that is LR(1) but not SLR(1).
 */
public class SLRGrammarTest {
	
	private Parser<Terminals> parser;
	
	@Before
	public void setUp() {
        final Grammar<Terminals> grammar = new Grammar<Terminals>(new ExampleGrammar.TerminalSetFactory(),
        		new Grammar.DefaultMapFactory<NonTerminalSymbol, Set<Production>>(),
                new Grammar.DefaultSetFactory<NonTerminalSymbol>());
        
        final NonTerminalSymbol nonTerminalA = grammar.getNonTerminalSymbol("A");
        final NonTerminalSymbol nonTerminalS = grammar.getNonTerminalSymbol("S");
        
		grammar.addProduction(nonTerminalA, new TestProductionHandler("A"), Terminals.D);
		
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), nonTerminalA, Terminals.A);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, nonTerminalA, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.D, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, Terminals.D, Terminals.A);
		
		parser = grammar.createParser(nonTerminalS, Terminals.EOF);
	}

	@Test
	public void testSLRGrammar1() {
		final Object ast1 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.D, Terminals.A)));
		
		Assert.assertEquals("S ::= [A ::= [D]] [A]", ast1);
	}

	@Test
	public void testSLRGrammar2() {
		final Object ast2 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.B, Terminals.D, Terminals.A)));
		
		Assert.assertEquals("S ::= [B] [D] [A]", ast2);
	}

	@Test
	public void testSLRGrammar3() {
		final Object ast3 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.B, Terminals.D, Terminals.C)));
		
		Assert.assertEquals("S ::= [B] [A ::= [D]] [C]", ast3);
	}

	@Test
	public void testSLRGrammarAll() {
		final Object ast1 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.D, Terminals.A)));
		final Object ast2 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.B, Terminals.D, Terminals.A)));
		final Object ast3 = parser.parse(new StaticLexer<>(Arrays.asList(Terminals.B, Terminals.D, Terminals.C)));
	}

}
