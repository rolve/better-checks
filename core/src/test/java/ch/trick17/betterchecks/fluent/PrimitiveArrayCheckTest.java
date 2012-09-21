package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MsgFormatId;

public class PrimitiveArrayCheckTest {
    
    @Test
    public void testAllTypes() {
        Check.that(new boolean[] {true}).isNotEmpty();
        Check.that(new byte[] {1}).isNotEmpty();
        Check.that(new char[] {'a'}).isNotEmpty();
        Check.that(new double[] {1.0}).isNotEmpty();
        Check.that(new float[] {1.0f}).isNotEmpty();
        Check.that(new int[] {1}).isNotEmpty();
        Check.that(new long[] {1}).isNotEmpty();
        Check.that(new short[] {1}).isNotEmpty();
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotEmpty() {
        Check.that(new int[] {0, 0}).isNotEmpty();
        Check.that(new int[] {0}).isNotEmpty();
        Check.that(new int[] {0, 0}).isNullOr().isNotEmpty();
        Check.that((String) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(new int[] {}).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_EMPTY, Exceptions
                .defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((int[]) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_NULL, Exceptions
                .defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLength() {
        Check.that(new int[] {0, 0, 0, 0, 0}).hasLength(5);
        Check.that(new int[] {0, 0}).hasLength(2);
        Check.that(new int[] {}).hasLength(0);
        
        Exception thrown = null;
        try {
            Check.that(new int[] {1, 2, 3}).hasLength(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_LENGTH, Exceptions
                .defaultArgName(), 2, "[1, 2, 3]"), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLengthBetween() {
        Check.that(new int[] {0, 0, 0, 0, 0}).hasLengthBetween(5, 5);
        Check.that(new int[] {0, 0, 0, 0, 0}).hasLengthBetween(0, 10);
        Check.that(new int[] {0, 0, 0, 0, 0}).hasLengthBetween(
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(new int[] {}).hasLengthBetween(0, 0);
        Check.that(new int[] {}).hasLengthBetween(-1, 1);
        Check.that(new int[] {}).hasLengthBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(new int[] {10, 20, 30}).hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN,
                Exceptions.defaultArgName(), 0, 2, "[10, 20, 30]"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(new int[] {-1, -2, -3}).hasLengthBetween(4,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN,
                Exceptions.defaultArgName(), 4, Integer.MAX_VALUE,
                "[-1, -2, -3]"), thrown.getMessage());
    }
}
