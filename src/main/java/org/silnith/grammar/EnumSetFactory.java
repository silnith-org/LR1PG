package org.silnith.grammar;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * A set factory for terminals that are {@code enum} types.
 * Using this factory can provide a substantial performance boost over the default
 * {@link java.util.HashSet} factory.
 * 
 * @param <T> the enum type
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class EnumSetFactory<T extends Enum<T>> implements SetFactory<T> {

    private final Class<T> elementType;

    public EnumSetFactory(final Class<T> elementType) {
        super();
        this.elementType = elementType;
    }

    @Override
    public Set<T> getNewSet() {
        return EnumSet.noneOf(elementType);
    }

    @Override
    public Set<T> getNewSet(final Collection<T> c) {
        if (c.isEmpty()) {
            return getNewSet();
        } else {
            return EnumSet.copyOf(c);
        }
    }
    
}