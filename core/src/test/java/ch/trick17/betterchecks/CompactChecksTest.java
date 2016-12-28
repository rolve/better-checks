package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.CompactChecks.check;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CompactChecksTest {
    
    @Test
    public void testCheckObject() {
        check(new Object()).isNotNull();
    }
    
    @Test
    public void testCheckString() {
        check("hello").is("hello");
    }
    
    @Test
    public void testCheckObjectArray() {
        check(new String[]{"hello", "world"}).hasLength(2);
    }
    
    @Test
    public void testCheckPrimitiveArray() {
        check(new boolean[0]).hasLength(0);
        check(new byte[0]).hasLength(0);
        check(new char[0]).hasLength(0);
        check(new double[0]).hasLength(0);
        check(new float[0]).hasLength(0);
        check(new int[0]).hasLength(0);
        check(new long[0]).hasLength(0);
        check(new short[0]).hasLength(0);
    }
    
    @Test
    public void testCheckCollection() {
        check(new ArrayList<Object>()).hasSize(0);
    }
    
    @Test
    public void testCheckDouble() {
        check(1.0).is(1.0);
    }
    
    @Test
    public void testCheckInt() {
        check(1).is(1);
    }
    
    @Test
    public void testCheckLong() {
        check(1L).is(1L);
    }
    
    @Test
    public void testCheckNumber() {
        check(new AtomicInteger(0)).is(0);
    }
    
    @Test
    public void testCheckURL() throws MalformedURLException {
        check(new URL("http://localhost")).hasProtocol("http");
    }
}
