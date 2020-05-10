package org.silnith.grammar;

import java.util.Collection;
import java.util.Set;

/**
 * A factory for instances of sets containing terminal or non-terminal symbols.
 * 
 * @param <T> the type of elements in the set
 */
public interface SetFactory<T> {
    
    /**
     * Returns a new empty set instance.
     * 
     * @return a new set
     */
    Set<T> getNewSet();
    
    /**
     * Returns a new set instance containing the provided elements.
     * 
     * @param c the elements to put into the set
     * @return a new set
     */
    Set<T> getNewSet(Collection<T> c);
    
}
