package org.silnith.grammar;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class CanonicalFactory<T> {
    
    private final ConcurrentMap<T, T> canonicalInstance;
    private final AtomicLong callCount;
    
    public CanonicalFactory() {
        super();
        this.canonicalInstance = new ConcurrentHashMap<>();
        this.callCount = new AtomicLong();
    }
    
    public T valueOf(final T t) {
        callCount.incrementAndGet();
        final T existingValue = canonicalInstance.putIfAbsent(t, t);
        if (existingValue == null) {
            return t;
        } else {
            return existingValue;
        }
    }
    
    public long getInstanceCount() {
        return canonicalInstance.size();
    }
    
    public long getCallCount() {
        return callCount.longValue();
    }
    
}
