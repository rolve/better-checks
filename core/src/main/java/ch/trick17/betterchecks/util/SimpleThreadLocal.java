package ch.trick17.betterchecks.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A {@link ThreadLocal} that uses the public no-arg constructor of the given
 * class to create an {@linkplain #initialValue() initial value}.
 * 
 * @author Michael Faes
 * @param <T>
 *            The type of object this ThreadLocal holds
 */
public final class SimpleThreadLocal<T> extends ThreadLocal<T> {
    
    private Constructor<? extends T> constructor;
    
    /**
     * Creates a simple thread local that uses the no-arg constructor of the
     * given class to create an {@link #initialValue()}.
     * 
     * @param clazz
     *            The class of the objects this thread local creates and holds.
     *            Must not be an abstract class, an interface, an array class, a
     *            primitive type or <code>void</code>, and must have a
     *            accessible no-argument constructor.
     * @throws IllegalArgumentException
     *             if the no-arg constructor of the given type can not be
     *             obtained.
     */
    public SimpleThreadLocal(final Class<? extends T> clazz) {
        try {
            constructor = clazz.getConstructor();
        } catch(final NoSuchMethodException e) {
            final String msg = "Class " + clazz.getName()
                    + " does not have a public no-arg constructor";
            throw new IllegalArgumentException(msg, e);
        }
    }
    
    /**
     * Returns the current thread's "initial value" for this thread-local
     * variable. This method will be invoked the first time a thread accesses
     * the variable with the {@link #get} method, unless the thread previously
     * invoked the {@link #set} method, in which case the
     * <code>initialValue</code> method will not be invoked for the thread.
     * Normally, this method is invoked at most once per thread, but it may be
     * invoked again in case of subsequent invocations of {@link #remove}
     * followed by {@link #get}.
     * <p>
     * This implementation returns a newly created object using the no-arg
     * constructor of the class provided at construction time.
     * 
     * @throws RuntimeException
     *             if the new instance can not be created. The cause of the
     *             exception is one of the following:
     *             {@link IllegalAccessException},
     *             {@link InvocationTargetException} or
     *             {@link InstantiationException}.
     */
    @Override
    protected T initialValue() {
        try {
            return constructor.newInstance();
        } catch(final IllegalAccessException e) {
            throw new AssertionError(e); // Not possible
        } catch(final InvocationTargetException e) {
            final String msg = "Could not create initial value for ThreadLocal with class: "
                    + constructor.getDeclaringClass().getName();
            throw new RuntimeException(msg, e);
        } catch(final InstantiationException e) {
            final String msg = "Could not create initial value for ThreadLocal with class: "
                    + constructor.getDeclaringClass().getName();
            throw new RuntimeException(msg, e);
        }
    }
}
