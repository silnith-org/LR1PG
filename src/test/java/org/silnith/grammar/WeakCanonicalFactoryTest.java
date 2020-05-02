package org.silnith.grammar;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;


public class WeakCanonicalFactoryTest {
    
    private final WeakCanonicalFactory<BigInteger> factory = new WeakCanonicalFactory<>();
    
    @Test
    public void testValueOf() {
        final int max = 100000000;
        for (int i = 0; i < max; i++) {
            factory.valueOf(BigInteger.valueOf(i / 1000));
        }
        
        System.out.println(factory.getCallCount());
        final long instanceCount = factory.getInstanceCount();
        System.out.println(instanceCount);
        assertEquals(max, factory.getCallCount());
        assertTrue(instanceCount < max / 1000);
    }
    
}
