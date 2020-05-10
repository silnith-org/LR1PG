package org.silnith.grammar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.silnith.grammar.example.ExampleGrammar;
import org.silnith.grammar.example.Terminals;

/**
 * Tests the parser on a grammar that is LR(1) but not LALR(1).
 */
public class LALRGrammarTest {
	
	private Parser<Terminals> parser;
	
	@Before
	public void setUp() {
        final Grammar<Terminals> grammar = new Grammar<Terminals>(new ExampleGrammar.TerminalSetFactory());
        
        final NonTerminalSymbol nonTerminalS = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbol nonTerminalE = grammar.getNonTerminalSymbol("E");
        final NonTerminalSymbol nonTerminalF = grammar.getNonTerminalSymbol("F");

		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.A, nonTerminalE, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.A, nonTerminalF, Terminals.D);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, nonTerminalF, Terminals.C);
		grammar.addProduction(nonTerminalS, new TestProductionHandler("S"), Terminals.B, nonTerminalE, Terminals.D);

		grammar.addProduction(nonTerminalE, new TestProductionHandler("E"), Terminals.E);

		grammar.addProduction(nonTerminalF, new TestProductionHandler("F"), Terminals.E);
		
		parser = grammar.createParser(nonTerminalS, Terminals.EOF);
	}
	
	@Test
	public void testLALRGrammar1() {
		final Object ast1 = parser.parse(new StaticLexer<>(Terminals.A, Terminals.E, Terminals.C));
		
		Assert.assertEquals("S ::= [A] [E ::= [E]] [C]", ast1);
	}
	
	@Test
	public void testLALRGrammar2() {
		final Object ast2 = parser.parse(new StaticLexer<>(Terminals.A, Terminals.E, Terminals.D));
		
		Assert.assertEquals("S ::= [A] [F ::= [E]] [D]", ast2);
	}
	
	@Test
	public void testLALRGrammar3() {
		final Object ast3 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.E, Terminals.C));
		
		Assert.assertEquals("S ::= [B] [F ::= [E]] [C]", ast3);
	}
	
	@Test
	public void testLALRGrammar4() {
		final Object ast4 = parser.parse(new StaticLexer<>(Terminals.B, Terminals.E, Terminals.D));
		
		Assert.assertEquals("S ::= [B] [E ::= [E]] [D]", ast4);
	}

}