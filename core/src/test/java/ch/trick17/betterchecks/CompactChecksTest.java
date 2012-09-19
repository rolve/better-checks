package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.CompactChecks.check;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class CompactChecksTest {
    
    @Test
    public void testCheckObject() {
        final Object argument = new Object();
        assertEquals(check(argument), Check.that(argument));
    }
    
    @Test
    public void testCheckString() {
        final String argument = "hello";
        assertEquals(check(argument), Check.that(argument));
    }
    
    @Test
    public void testCheckObjectArray() {
        final String[] argument = {"hello", "world"};
        assertEquals(check(argument), Check.that(argument));
    }
    
    @Test
    public void testCheckCollection() {
        final Collection<Object> argument = new ArrayList<Object>();
        assertEquals(check(argument), Check.that(argument));
    }
}
