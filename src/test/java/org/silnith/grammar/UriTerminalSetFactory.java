package org.silnith.grammar;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.silnith.grammar.uri.token.UriTerminalType;

public class UriTerminalSetFactory implements SetFactory<UriTerminalType> {

    @Override
    public Set<UriTerminalType> getNewSet() {
        return EnumSet.noneOf(UriTerminalType.class);
    }

    @Override
    public Set<UriTerminalType> getNewSet(final Collection<UriTerminalType> c) {
        if (c.isEmpty()) {
            return EnumSet.noneOf(UriTerminalType.class);
        } else {
            return EnumSet.copyOf(c);
        }
    }
    
}