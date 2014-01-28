package ch.trick17.betterchecks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CheckTest {
    
    @Test
    public void testArguments() {
        Check.arguments(true, "Everything ok");
        
        Exception thrown = null;
        try {
            Check.arguments(false, "Those arguments are no good");
        } catch(final Exception e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(IllegalArgumentException.class, thrown.getClass());
        assertEquals("Those arguments are no good", thrown.getMessage());
    }
    
    @Test
    public void testState() {
        Check.state(true, "Everything ok");
        
        Exception thrown = null;
        try {
            Check.state(false, "this is not state of the art");
        } catch(final Exception e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(IllegalStateException.class, thrown.getClass());
        assertEquals("this is not state of the art", thrown.getMessage());
    }
}
