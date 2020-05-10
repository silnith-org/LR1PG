package org.silnith.grammar;

import java.util.Map;

/**
 * @param <K> the type of map keys
 */
public interface MapFactory<K> {

    /**
     * @param <V> the type of map values
     * @return a new map
     */
    <V> Map<K, V> getNewMap();

}
