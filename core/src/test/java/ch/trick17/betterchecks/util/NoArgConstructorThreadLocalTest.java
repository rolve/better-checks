package ch.trick17.betterchecks.util;

import static junit.framework.Assert.*;

import org.junit.Test;

import ch.trick17.betterchecks.fluent.ObjectCheck;

public class NoArgConstructorThreadLocalTest {
    
    @Test
    @SuppressWarnings("null")
    public void testNoArgConstructorThreadLocal() {
        new NoArgConstructorThreadLocal<Object>(Object.class).get();
        new NoArgConstructorThreadLocal<Object>(String.class).get();
        new NoArgConstructorThreadLocal<Object>(ObjectCheck.class).get();
        
        RuntimeException thrown = null;
        try {
            final Object threadLocal = new NoArgConstructorThreadLocal<Integer>(
                    Integer.class);
            threadLocal.toString();
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(thrown.getMessage(),
                "Could not access the no-arg constructor of class java.lang.Integer");
        assertTrue(thrown.getCause() instanceof ReflectiveOperationException);
        
        thrown = null;
        final ThreadLocal<ThrowingHelper> threadLocal = new NoArgConstructorThreadLocal<ThrowingHelper>(
                ThrowingHelper.class);
        try {
            threadLocal.get();
        } catch(final RuntimeException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(
                thrown.getMessage(),
                "Could not create initial value for ThreadLocal with class: ch.trick17.betterchecks.util.NoArgConstructorThreadLocalTest$ThrowingHelper");
        assertTrue(thrown.getCause() instanceof ReflectiveOperationException);
    }
    
    private static class ThrowingHelper {
        
        @SuppressWarnings("unused")
        public ThrowingHelper() {
            throw new RuntimeException();
        }
        
    }
}
