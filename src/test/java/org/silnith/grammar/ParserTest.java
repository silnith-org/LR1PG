package org.silnith.grammar;

import org.junit.Test;


public class ParserTest {
    
    @Test
    public void testParse() {
        final Grammar<CharacterLiteral, NonTerminalSymbol> grammar = new Grammar<>();
        
        final CharacterLiteral eq = new CharacterLiteral("=", '=');
        final CharacterLiteral x = new CharacterLiteral("x", 'x');
        final CharacterLiteral star = new CharacterLiteral("*", '*');
        final CharacterLiteral eof = new CharacterLiteral("$", '$');
        
        final NonTerminalSymbol s = new NonTerminal("S");
        final NonTerminalSymbol v = new NonTerminal("V");
        final NonTerminalSymbol e = new NonTerminal("E");
        
        grammar.addProduction(s, new TestProductionHandler("S"), v, eq, e);
        grammar.addProduction(s, new TestProductionHandler("S"), e);
        grammar.addProduction(e, new TestProductionHandler("E"), v);
        grammar.addProduction(v, new TestProductionHandler("V"), x);
        grammar.addProduction(v, new TestProductionHandler("V"), star, e);
        
        grammar.compute();
        
        final Parser<CharacterLiteral> parser = grammar.createParser(s, eof);
        
        parser.parse(new StaticLexer<>(star, x, eq, star, x));
        
//        fail("Not yet implemented");
    }
    
}
