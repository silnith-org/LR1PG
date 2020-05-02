package org.silnith.grammar;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakCanonicalFactory<T> {
    
    private final Map<T, WeakReference<T>> canonicalInstance;

    private long callCount;

    public WeakCanonicalFactory() {
        super();
        this.canonicalInstance = new WeakHashMap<T, WeakReference<T>>();
        this.callCount = 0;
    }
    
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
    
    public synchronized long getInstanceCount() {
        return canonicalInstance.size();
    }
    
    public synchronized long getCallCount() {
        return callCount;
    }
    
}
