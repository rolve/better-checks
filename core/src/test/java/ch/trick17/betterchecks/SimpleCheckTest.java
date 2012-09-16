package ch.trick17.betterchecks;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SimpleCheckTest {
    
    private static int one() {
        return 1;
    }
    
    @SuppressWarnings("null")
    @Test
    public void testThat() {
        Check.that(1 == one(), "standard math");
        
        Exception thrown = null;
        try {
            Check.that(0 == one(), "dumb");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals("dumb", thrown.getMessage());
    }
    
    @Test
    public void testNotNullObject() {
        fail("Not yet implemented");
    }
    
    @SuppressWarnings("null")
    @Test
    public void testNotNullObjectString() {
        Check.notNull(new Object(), "my object");
        
        Exception thrown = null;
        try {
            Check.notNull(null, "my object");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals("my object must not be null", thrown.getMessage());
    }
    
    @Test
    public void testNotEmptyString() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testNotEmptyStringString() {
        fail("Not yet implemented");
    }
    
}
