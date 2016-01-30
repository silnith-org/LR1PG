package org.silnith.grammar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;


public class IntegerSetTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNegativeNumber() {
        final IntegerSet set = new IntegerSet();
        
        set.add( -1);
    }
    
    @Test
    public void testAddZero() {
        final IntegerSet set = new IntegerSet();
        
        set.add(0);
        
        assertTrue(set.contains(0));
    }
    
    @Test
    public void testAddOne() {
        final IntegerSet set = new IntegerSet();
        
        set.add(1);
        
        assertTrue(set.contains(1));
    }
    
    @Test
    public void testAddTwo() {
        final IntegerSet set = new IntegerSet();
        
        set.add(2);
        
        assertTrue(set.contains(2));
    }
    
    @Test
    public void testAddSixtyThree() {
        final IntegerSet set = new IntegerSet();
        
        set.add(63);
        
        assertTrue(set.contains(63));
    }
    
    @Test
    public void testAddSixtyFour() {
        final IntegerSet set = new IntegerSet();
        
        set.add(64);
        
        assertTrue(set.contains(64));
    }
    
    @Test
    public void testIteratorWithStuff() {
        final IntegerSet set = new IntegerSet();
        
        set.add(0);
        set.add(7);
        set.add(8);
        set.add(9);
        set.add(12);
        set.add(124);
        
        assertEquals(new HashSet<>(Arrays.asList(0, 7, 8, 9, 12, 124)), set);
    }
    
    @Test
    public void testAddAll() {
        final IntegerSet set = new IntegerSet();
        
        set.addAll(Arrays.asList(0, 7, 8, 9, 12, 124));
        
        assertEquals(new HashSet<>(Arrays.asList(0, 7, 8, 9, 12, 124)), set);
    }
    
}
