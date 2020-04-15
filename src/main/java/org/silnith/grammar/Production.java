package org.silnith.grammar;

import java.util.Arrays;
import java.util.List;


public class Production {
    
    private final ProductionHandler productionHandler;
    
    private final List<SymbolMatch> symbols;
    
    private final int hashCode;
    
    public Production(final ProductionHandler productionHandler, final SymbolMatch... symbols) {
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
    public List<SymbolMatch> getSymbols() {
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
