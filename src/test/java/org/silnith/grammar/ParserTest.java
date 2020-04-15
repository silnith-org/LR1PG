package org.silnith.grammar;

import org.junit.Test;


public class ParserTest {
    
    @Test
    public void testParse() {
        final Grammar<CharacterLiteral> grammar = new Grammar<>();
        
        final TerminalSymbolMatch eq = new CharacterLiteral("=", '=');
        final TerminalSymbolMatch x = new CharacterLiteral("x", 'x');
        final TerminalSymbolMatch star = new CharacterLiteral("*", '*');
        final CharacterLiteral eof = new CharacterLiteral("$", '$');
        
        final NonTerminalSymbolMatch s = grammar.getNonTerminalSymbol("S");
        final NonTerminalSymbolMatch v = grammar.getNonTerminalSymbol("V");
        final NonTerminalSymbolMatch e = grammar.getNonTerminalSymbol("E");
        
        grammar.addProduction(s, new TestProductionHandler("S"), v, eq, e);
        grammar.addProduction(s, new TestProductionHandler("S"), e);
        grammar.addProduction(e, new TestProductionHandler("E"), v);
        grammar.addProduction(v, new TestProductionHandler("V"), x);
        grammar.addProduction(v, new TestProductionHandler("V"), star, e);
        
        grammar.compute();
        
        final Parser<CharacterLiteral> parser = grammar.createParser(s, eof);
        
        parser.parse(new StaticLexer(star, x, eq, star, x));
        
//        fail("Not yet implemented");
    }
    
}
