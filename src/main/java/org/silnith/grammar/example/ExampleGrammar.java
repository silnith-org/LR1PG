package org.silnith.grammar.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.silnith.grammar.DataStackElement;
import org.silnith.grammar.Grammar;
import org.silnith.grammar.NonTerminalSymbol;
import org.silnith.grammar.Parser;
import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.SetFactory;


public class ExampleGrammar {
    
    public static final class StringProductionHandler implements ProductionHandler {

    	final String string;

    	public StringProductionHandler(final String string) {
			super();
			this.string = string;
		}

		@Override
		public Object handleReduction(final List<DataStackElement> rightHandSide) {
//			String.join(", ", rightHandSide);
			
			return string;
		}

    }

	public static class TerminalSetFactory implements SetFactory<Terminals> {
        
        @Override
        public Set<Terminals> getNewSet() {
            return EnumSet.noneOf(Terminals.class);
        }
        
        @Override
        public Set<Terminals> getNewSet(final Collection<Terminals> c) {
            if (c.isEmpty()) {
                return EnumSet.noneOf(Terminals.class);
            } else {
                return EnumSet.copyOf(c);
            }
        }
        
    }
    
    /**
     * An example program demonstrating a parser generated for a simple grammar.
     * 
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        final Grammar<Terminals> grammar = new Grammar<>(new TerminalSetFactory());
        
        final NonTerminalSymbol nonTerminal = grammar.getNonTerminalSymbol("S");
        
		grammar.addProduction(nonTerminal, new StringProductionHandler("S = A + B + C"), Terminals.A, Terminals.B, Terminals.C);
        
        final Parser<Terminals> parser = grammar.createParser(nonTerminal, Terminals.EOF);
        
        final List<Terminals> input = Arrays.asList(Terminals.A, Terminals.B, Terminals.C, Terminals.EOF);
        
//        final Object abstractSyntaxTree = parser.parse(new StaticLexer(input));
//        
//        System.out.println(abstractSyntaxTree);
    }
    
}
