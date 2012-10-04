package ch.trick17.betterchecks.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A {@link ThreadLocal} that uses the no-arg constructor of the class passed to
 * it at creation time to create {@link #initialValue()}s.
 * 
 * @author Michael Faes
 * @param <T>
 *            The type of object this ThreadLocal holds
 */
public final class SimpleThreadLocal<T> extends ThreadLocal<T> {
    
    private Constructor<? extends T> constructor;
    
    public SimpleThreadLocal(final Class<? extends T> clazz) {
        try {
            constructor = clazz.getConstructor();
        } catch(final NoSuchMethodException e) {
            final String msg = "Could not access the no-arg constructor of class "
                    + clazz.getName();
            throw new RuntimeException(msg, e);
        }
    }
    
    @Override
    protected T initialValue() {
        try {
            return constructor.newInstance();
        } catch(final IllegalAccessException e) {
            final String msg = "Could not create initial value for ThreadLocal with class: "
                    + constructor.getDeclaringClass().getName();
            throw new RuntimeException(msg, e);
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