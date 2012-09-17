package ch.trick17.betterchecks.util;

import java.lang.reflect.Constructor;

/**
 * A {@link ThreadLocal} that uses the no-arg constructor of the class passed to
 * it at creation time to create {@link #initialValue()}s.
 * 
 * @author Michael Faes
 * @param <T>
 *            The type of object this ThreadLocal holds
 */
public class NoArgConstructorThreadLocal<T> extends ThreadLocal<T> {
    
    private Constructor<T> constructor;
    
    public NoArgConstructorThreadLocal(final Class<T> clazz) {
        try {
            constructor = clazz.getConstructor();
        } catch(final ReflectiveOperationException e) {
            final String msg = "Could not access the no-arg constructor of class "
                    + clazz.getName();
            throw new RuntimeException(msg, e);
        }
    }
    
    @Override
    protected T initialValue() {
        try {
            return constructor.newInstance();
        } catch(final ReflectiveOperationException e) {
            final String msg = "Could not create initial value for ThreadLocal with class: "
                    + constructor.getDeclaringClass().getName();
            throw new RuntimeException(msg, e);
        }
    }
}
