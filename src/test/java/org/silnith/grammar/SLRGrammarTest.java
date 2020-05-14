package org.silnith.grammar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.silnith.grammar.example.Terminals;

/**
 * This constructs a parser for a grammar that is LR(1) but not SLR(1).
 */
public class SLRGrammarTest {
	
	private Parser<Terminals> parser;
	
	@Before
	public void setUp() {
        final Grammar<Terminals> grammar = new Grammar<Terminals>(new EnumSetFactory<>(Terminals.class));
        
        final NonTerminalSymbol nonTerminalA = grammar.getNonTerminalSymbol("A");
        final NonTerminalSymbol nonTerminalS = grammar.getNonTerminalSymbol("S");
        
		grammar.addProduction(nonTerminalA, new TestProductionHandler("A"), Terminals.D);
		
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), nonTerminalA, Terminals.A);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, nonTerminalA, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.D, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, Terminals.D, Terminals.A);
        grammar.setStartSymbol(nonTerminalS);
        grammar.setEndOfFileSymbol(Terminals.EOF);
		
		parser = grammar.createParser();
	}

	@Test
	public void testSLRGrammar1() {
		final Object ast1 = parser.parse(new StaticLexer<>(Terminals.D, Terminals.A));
		
		Assert.assertEquals("S ::= [A ::= [D]] [A]", ast1);
	}

	@Test
	public void testSLRGrammar2() {
		final Object ast2 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.D, Terminals.A));
		
		Assert.assertEquals("S ::= [B] [D] [A]", ast2);
	}

	@Test
	public void testSLRGrammar3() {
		final Object ast3 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.D, Terminals.C));
		
		Assert.assertEquals("S ::= [B] [A ::= [D]] [C]", ast3);
	}

	@Test
	public void testSLRGrammarAll() {
		final Object ast1 = parser.parse(new StaticLexer<>(Terminals.D, Terminals.A));
		final Object ast2 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.D, Terminals.A));
		final Object ast3 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.D, Terminals.C));
	}

}
