package org.silnith.grammar;

import java.util.Arrays;
import java.util.List;


/**
 * A sequence of symbols that can be reduced.  This contains only the symbols that can be reduced as well as the handler
 * that performs the reduction.  This does not contain the type of non-terminal that will replace the symbols reduced.
 */
public class Production {
    
    private final ProductionHandler productionHandler;
    
    private final List<Symbol> symbols;
    
    private final int hashCode;
    
    /**
     * Creates a new production.
     * 
     * @param productionHandler the production handler
     * @param symbols the list of symbols
     */
    public Production(final ProductionHandler productionHandler, final Symbol... symbols) {
        super();
        if (productionHandler == null) {
        	throw new IllegalArgumentException();
        }
        this.productionHandler = productionHandler;
        this.symbols = Arrays.asList(symbols);
        this.hashCode = this.symbols.hashCode();
    }
    
    /**
     * Returns the handler responsible for manipulating the data stack whenever
     * a reduction is performed using this production.
     * 
     * @return the production handler
     */
    public ProductionHandler getProductionHandler() {
        return productionHandler;
    }
    
    /**
     * Returns the right-hand side of the production.
     * 
     * @return the symbols in the right hand of the production
     */
    public List<Symbol> getSymbols() {
        return symbols;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Production) {
            final Production other = (Production) obj;
            if (hashCode != other.hashCode) {
                return false;
            }
            return symbols.equals(other.symbols);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(symbols);
    }
    
}
