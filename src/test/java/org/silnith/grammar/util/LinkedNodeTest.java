package org.silnith.grammar.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;


public class LinkedNodeTest {
    
    /*
     * Methods from java.lang.Object
     */
    
    @Test
    public void testEquals() {
        final LinkedNode<String> list1 = new LinkedNode<String>("foo");
        final LinkedNode<String> list2 = new LinkedNode<String>("foo");
        
        assertTrue(list1.equals(list2));
    }
    
    @Test
    public void testHashCode() {
        final LinkedNode<String> list1 = new LinkedNode<String>("foo");
        final LinkedNode<String> list2 = new LinkedNode<String>("foo");
        
        assertEquals(list1.hashCode(), list2.hashCode());
    }
    
    @Test
    public void testEqualsUsingDifferentConstructor() {
        final LinkedNode<String> list1 = new LinkedNode<String>("foo");
        final LinkedNode<String> list2 = new LinkedNode<String>("foo", null);
        
        assertTrue(list1.equals(list2));
    }
    
    @Test
    public void testHashCodeUsingDifferentConstructor() {
        final LinkedNode<String> list1 = new LinkedNode<String>("foo");
        final LinkedNode<String> list2 = new LinkedNode<String>("foo", null);
        
        assertEquals(list1.hashCode(), list2.hashCode());
    }
    
    @Test
    public void testToString() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertEquals("[foo]", list.toString());
    }
    
    /*
     * Methods from java.lang.Iterable
     */
    
    @Test
    public void testIteratorExists() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        assertNotNull(iterator);
    }
    
    @Test
    public void testIteratorHasNext() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
    }
    
    @Test
    public void testIteratorHasNextIsIdempotent() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        for (int i = 0; i < 10; i++ ) {
            assertTrue("iteration " + i, iterator.hasNext());
        }
    }
    
    @Test
    public void testIteratorNext() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        assertEquals("foo", iterator.next());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorNextRemoveFails() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
    }
    
    @Test
    public void testIteratorSecondHasNext() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        iterator.next();
        assertFalse(iterator.hasNext());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorSecondNextFails() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        final Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.next();
    }
    
    /*
     * Methods in java.util.Collection
     */
    
    @Test
    public void testSize() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertEquals(1, list.size());
    }
    
    @Test
    public void testIsEmpty() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertFalse(list.isEmpty());
    }
    
    @Test
    public void testContains() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertTrue(list.contains("foo"));
    }
    
    @Test
    public void testContainsMiss() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertFalse(list.contains("bar"));
    }
    
    @Test
    public void testToArray() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertArrayEquals(new Object[] { "foo" }, list.toArray());
    }
    
    @Test
    public void testToTypedArray() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertArrayEquals(new String[] { "foo" }, list.toArray(new String[0]));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.add("bar");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.remove("foo");
    }
    
    @Test
    public void testRemoveMiss() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertFalse(list.remove("bar"));
    }
    
    @Test
    public void testContainsAll() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertTrue(list.containsAll(Collections.singleton("foo")));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.addAll(Collections.singleton("bar"));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.removeAll(Collections.singleton("foo"));
    }
    
    @Test
    public void testRemoveAllMiss() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertFalse(list.removeAll(Collections.singleton("bar")));
    }
    
    @Test
    public void testRetainAll() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertFalse(list.retainAll(Collections.singleton("foo")));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAllMiss() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.retainAll(Collections.singleton("bar"));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.clear();
    }
    
    /*
     * Methods from java.util.List
     */
    
    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllAtIndex() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.addAll(0, Collections.singleton("bar"));
    }
    
    @Test
    public void testGet() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertEquals("foo", list.get(0));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithUnderflow() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.get( -1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithOverflow() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.get(1);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.set(0, "bar");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testAddAtIndex() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.add(0, "bar");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveIndex() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.remove(0);
    }
    
    @Test
    public void testIndexOf() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertEquals(0, list.indexOf("foo"));
    }
    
    @Test
    public void testLastIndexOf() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertEquals(0, list.lastIndexOf("foo"));
    }
    
    @Test
    public void testListIterator() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertNotNull(list.listIterator());
    }
    
    @Test
    public void testListIteratorAtIndex() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertNotNull(list.listIterator(0));
    }
    
    @Test
    public void testListIteratorAtIndexEnd() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertNotNull(list.listIterator(1));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexOverflow() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.listIterator(2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexUnderflow() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        list.listIterator( -1);
    }
    
    @Test
    public void testListIteratorAtIndexEndWithLongList() {
        final LinkedNode<String> list =
                new LinkedNode<String>("foo", new LinkedNode<String>("bar", new LinkedNode<String>("baz")));
                
        assertNotNull(list.listIterator(3));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexOverflowWithLongList() {
        final LinkedNode<String> list =
                new LinkedNode<String>("foo", new LinkedNode<String>("bar", new LinkedNode<String>("baz")));
                
        list.listIterator(4);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testListIteratorAtIndexUnderflowWithLongList() {
        final LinkedNode<String> list =
                new LinkedNode<String>("foo", new LinkedNode<String>("bar", new LinkedNode<String>("baz")));
                
        list.listIterator( -1);
    }
    
    @Test
    public void testSubList() {
        final LinkedNode<String> list = new LinkedNode<String>("foo");
        
        assertNotNull(list.subList(0, 1));
    }
    
    @Test
    public void testSizeForSingletonConstructorWithNullValue() {
        final LinkedNode<String> list = new LinkedNode<String>(null);
        
        assertEquals(1, list.size());
    }
    
    @Test
    public void testSizeForConsConstructorWithNullList() {
        final LinkedNode<String> list = new LinkedNode<String>("foo", null);
        
        assertEquals(1, list.size());
    }
    
    @Test
    public void testSizeForConsConstructorWithNullValueAndNullList() {
        final LinkedNode<String> list = new LinkedNode<String>(null, null);
        
        assertEquals(1, list.size());
    }
    
}
