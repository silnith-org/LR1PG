package org.silnith.grammar;

import java.util.Collection;
import java.util.Set;

/**
 * A factory for instances of sets containing terminal or non-terminal symbols.
 * 
 * @param <V> the type of elements in the set
 */
public interface SetFactory<V> {
    
    /**
     * Returns a new empty set instance.
     * 
     * @return a new set
     */
    Set<V> getNewSet();
    
    /**
     * Returns a new set instance containing the provided elements.
     * 
     * @param c the elements to put into the set
     * @return a new set
     */
    Set<V> getNewSet(Collection<V> c);
    
}