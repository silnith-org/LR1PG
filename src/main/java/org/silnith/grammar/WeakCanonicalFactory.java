package org.silnith.grammar;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A filtering object that takes instances of a type that implements {@link Object#equals(Object)}
 * and {@link Object#hashCode()} in a way that is <dfn>consistent with equals</dfn> and converts
 * instances that are equal so that they refer to a single canonical instance.
 * 
 * <p>The purpose of this class is to allow a program to make sure that for a given type, of all instances that are
 * equal to each other, only one single instance is kept in memory and used for all operations.
 * 
 * <p><code><pre>
 * WeakCanonicalFactory&lt;T&gt; factory = new WeakCanonicalFactory&lt;&gt;();
 * 
 * final T a = factory.valueOf(new T("a", "b", "c"));
 * final T b = factory.valueOf(new T("a", "b", "c"));
 * final T c = factory.valueOf(new T("a", "b", "c"));
 * 
 * assert a == b;
 * assert a == c;
 * </pre></code>
 * 
 * <p>This functions more as a cache than a permanent store.  If all references to a canonical value are removed other
 * than those in this factory, then this factory will not prevent the garbage collector from reclaiming the
 * otherwise-unused instance.  In that case, any future calls to {@link #valueOf(Object)} will return the new instance
 * rather than the previous instance.  Therefore it is safe to use this in situations with unbounded values provided
 * that only a small subset of those values are used concurrently.  For example, a text parser could use this to
 * de-duplicate common substrings while still allowing those strings to be reclaimed when the parser is garbage collected.
 * 
 * @param <T> the type for which to provide canonical instances.  The type must implement {@link Object#hashCode()} to be <dfn>consistent with equals</dfn>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class WeakCanonicalFactory<T> {
    
    private final Map<T, WeakReference<T>> canonicalInstance;

    private long callCount;

    /**
     * Creates a new canonical factory.
     */
    public WeakCanonicalFactory() {
        super();
        this.canonicalInstance = new WeakHashMap<T, WeakReference<T>>();
        this.callCount = 0;
    }
    
    /**
     * Returns the canonical instance for a value.
     * 
     * @param t the value for which to get a canonical instance
     * @return the canonical instance of that value
     */
    public synchronized T valueOf(final T t) {
        callCount++;

        /*
         * As long as the strong reference t exists, this weak reference will not vanish.
         */
        final WeakReference<T> newWeakReference = new WeakReference<>(t);
        
        final WeakReference<T> existingWeakReference = canonicalInstance.get(t);
        if (existingWeakReference == null) {
            /*
             * There was no existing entry.  Create one.
             */
            final WeakReference<T> displaced = canonicalInstance.put(t, newWeakReference);
            
            /*
             * Because this method is synchronized, it is not possible for another thread to preemptively insert a new
             * canonical instance.
             */
            assert displaced == null;
            
            return t;
        } else {
            /*
             * There was an existing entry, check if it is still valid.
             */
            final T existingT = existingWeakReference.get();
            if (existingT == null) {
                /*
                 * The weak reference in the canonical map vanished.  Continue with the newly-created one.
                 */
                final WeakReference<T> displaced = canonicalInstance.put(t, newWeakReference);
                
                /*
                 * It is possible that the weak hash map removed the entry because the weak
                 * reference was cleaned up, so the put could return the same reference or
                 * it could return null.
                 */
                assert displaced == existingWeakReference || displaced == null;

                return t;
            } else {
                /*
                 * The weak reference in the canonical map is valid, return it as a strong reference.
                 */
                return existingT;
            }
        }
    }
    
    /**
     * Returns the number of canonical instances currently stored.
     * 
     * @return the number of distinct values of the type that currently exist
     */
    public synchronized long getInstanceCount() {
        return canonicalInstance.size();
    }
    
    /**
     * Returns the number of times a value was converted to a canonical instance.  This includes the first time a value
     * is seen, in which case that value is stored and returned for subsequent equal values.
     * 
     * @return the number of times a value has been canonicalized
     */
    public synchronized long getCallCount() {
        return callCount;
    }
    
}
