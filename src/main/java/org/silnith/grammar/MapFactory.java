package org.silnith.grammar;

import java.util.Map;

/**
 * A factory for instances of maps.
 * 
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public interface MapFactory<K, V> {

    /**
     * Returns a new empty map instance.
     * 
     * @return a new map
     */
    Map<K, V> getNewMap();

    /**
     * Returns a new map instance containing the provided elements.
     * 
     * @param m the elements to put into the map
     * @return a new map
     */
    Map<K, V> getNewMap(Map<K, V> m);
    
}