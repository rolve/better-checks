package ch.trick17.betterchecks.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import ch.trick17.betterchecks.fluent.ObjectCheck;

public class SimpleThreadLocalTest {
    
    @Test
    public void testSimpleThreadLocal() {
        new SimpleThreadLocal<Object>(Object.class).get();
        new SimpleThreadLocal<String>(String.class).get();
        new SimpleThreadLocal<ObjectCheck>(ObjectCheck.class).get();
    }
    
    @Test
    public void testSimpleThreadLocalNoConstructor() {
        RuntimeException thrown = null;
        try {
            @SuppressWarnings("unused") final Object tl = new SimpleThreadLocal<Integer>(
                    Integer.class);
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(
                "Class java.lang.Integer does not have a public no-arg constructor",
                thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NoSuchMethodException);
    }
    
    @Test
    public void testSimpleThreadLocalPrivateConstructor() {
        RuntimeException thrown = null;
        try {
            @SuppressWarnings("unused") final Object tl = new SimpleThreadLocal<PrivateConstructor>(
                    PrivateConstructor.class);
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals("Class " + PrivateConstructor.class.getName()
                + " does not have a public no-arg constructor", thrown
                .getMessage());
        assertTrue(thrown.getCause() instanceof NoSuchMethodException);
    }
    
    private static class PrivateConstructor {
        private PrivateConstructor() {}
    }
    
    @Test
    public void testSimpleThreadLocalException() {
        RuntimeException thrown = null;
        final ThreadLocal<?> threadLocal = new SimpleThreadLocal<ThrowsException>(
                ThrowsException.class);
        try {
            threadLocal.get();
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(
                "Could not create initial value for ThreadLocal with class: "
                        + ThrowsException.class.getName(), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof InvocationTargetException);
    }
    
    private static class ThrowsException {
        @SuppressWarnings("unused")
        public ThrowsException() {
            throw new RuntimeException();
        }
    }
    
    @Test
    public void testSimpleThreadLocalAbstractClass() {
        RuntimeException thrown = null;
        final ThreadLocal<?> threadLocal = new SimpleThreadLocal<Abstract>(
                Abstract.class);
        try {
            threadLocal.get();
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(
                "Could not create initial value for ThreadLocal with class: "
                        + Abstract.class.getName(), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof InstantiationException);
    }
    
    private static abstract class Abstract {
        @SuppressWarnings("unused")
        public Abstract() {}
    }
}
