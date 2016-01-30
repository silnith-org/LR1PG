package org.silnith.grammar;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


public class ExampleGrammar extends Grammar<Terminals> {
    
    private static class TerminalSetFactory implements SetFactory<Terminals> {
        
        @Override
        public Set<Terminals> getNewSet() {
            return EnumSet.noneOf(Terminals.class);
        }
        
        @Override
        public Set<Terminals> getNewSet(final Collection<Terminals> c) {
            return EnumSet.copyOf(c);
        }
        
    }
    
    public ExampleGrammar() {
        super(new TerminalSetFactory(), new DefaultMapFactory<NonTerminalSymbol, Set<Production>>(),
                new DefaultSetFactory<NonTerminalSymbol>());
    }
    
    public static void main(final String[] args) {
        final Grammar<Terminals> grammar = new ExampleGrammar();
        
        grammar.addProduction(new NonTerminal("S"), null, Terminals.A, Terminals.B, Terminals.C);
        
        final Parser<Terminals> parser = grammar.createParser(new NonTerminal("S"), Terminals.EOF);
        
        final List<Terminals> input = Arrays.asList(Terminals.A, Terminals.B, Terminals.C, Terminals.EOF);
        
        parser.parse(input.iterator());
    }
    
}
