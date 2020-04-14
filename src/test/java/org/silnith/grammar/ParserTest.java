package org.silnith.grammar;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ParserTest {
    
    @Test
    public void testParse() {
        final Grammar<Terminal> grammar = new Grammar<>();
        
        final Terminal eq = new CharacterLiteral("=", '=');
        final Terminal x = new CharacterLiteral("x", 'x');
        final Terminal star = new CharacterLiteral("*", '*');
        final Terminal eof = new CharacterLiteral("$", '$');
        
        final NonTerminal s = new NonTerminal("S");
        final NonTerminal v = new NonTerminal("V");
        final NonTerminal e = new NonTerminal("E");
        
        grammar.addProduction(s, new TestProductionHandler("S"), v, eq, e);
        grammar.addProduction(s, new TestProductionHandler("S"), e);
        grammar.addProduction(e, new TestProductionHandler("E"), v);
        grammar.addProduction(v, new TestProductionHandler("V"), x);
        grammar.addProduction(v, new TestProductionHandler("V"), star, e);
        
        grammar.compute();
        
        final Parser<Terminal> parser = grammar.createParser(s, eof);
        
        final List<Terminal> input = Arrays.asList(star, x, eq, star, x);
        
        parser.parse(new StaticLexer<>(input));
        
//        fail("Not yet implemented");
    }
    
}
