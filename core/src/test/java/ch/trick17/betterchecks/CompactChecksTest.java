package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.CompactChecks.check;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void testCheckPrimitiveArray() {
        assertEquals(check(new boolean[]{}), Check.that(new boolean[]{}));
        assertEquals(check(new byte[]{}), Check.that(new byte[]{}));
        assertEquals(check(new char[]{}), Check.that(new char[]{}));
        assertEquals(check(new double[]{}), Check.that(new double[]{}));
        assertEquals(check(new float[]{}), Check.that(new float[]{}));
        assertEquals(check(new int[]{}), Check.that(new int[]{}));
        assertEquals(check(new long[]{}), Check.that(new long[]{}));
        assertEquals(check(new short[]{}), Check.that(new short[]{}));
    }
    
    @Test
    public void testCheckCollection() {
        final Collection<Object> argument = new ArrayList<Object>();
        assertEquals(check(argument), Check.that(argument));
    }
    
    @Test
    public void testCheckDouble() {
        assertEquals(check(1.0), Check.that(1.0));
    }
    
    @Test
    public void testCheckInt() {
        assertEquals(check(1), Check.that(1));
    }
    
    @Test
    public void testCheckLong() {
        assertEquals(check(1L), Check.that(1L));
    }
    
    @Test
    public void testCheckNumber() {
        final AtomicInteger atomicInt = new AtomicInteger();
        assertEquals(check(atomicInt), Check.that(atomicInt));
    }
    
    @Test
    public void testCheckURL() throws MalformedURLException {
        final URL url = new URL("http://localhost");
        assertEquals(check(url), Check.that(1L));
    }
    
}
