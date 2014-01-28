package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class PrimitiveArrayCheckTest {
    
    @Test
    public void testAllTypes() {
        Check.that(new boolean[]{true}).isNotEmpty();
        Check.that(new byte[]{1}).isNotEmpty();
        Check.that(new char[]{'a'}).isNotEmpty();
        Check.that(new double[]{1.0}).isNotEmpty();
        Check.that(new float[]{1.0f}).isNotEmpty();
        Check.that(new int[]{1}).isNotEmpty();
        Check.that(new long[]{1}).isNotEmpty();
        Check.that(new short[]{1}).isNotEmpty();
    }
    
    @Test
    public void testIsNotEmpty() {
        Check.that(new int[]{0, 0}).isNotEmpty();
        Check.that(new int[]{0}).isNotEmpty();
        Check.that(new int[]{0, 0}).isNullOr().isNotEmpty();
        Check.that((String) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(new int[]{}).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new int[]{1}).not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "[1]"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((int[]) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasLength() {
        Check.that(new int[]{0, 0, 0, 0, 0}).hasLength(5);
        Check.that(new int[]{0, 0}).hasLength(2);
        Check.that(new int[]{}).hasLength(0);
        
        Exception thrown = null;
        try {
            Check.that(new int[]{1, 2, 3}).hasLength(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH, false,
                Exceptions.defaultArgName(), 2, "[1, 2, 3]"), thrown
                .getMessage());
    }
    
    @Test
    public void testHasLengthBetween() {
        Check.that(new int[]{0, 0, 0, 0, 0}).hasLengthBetween(5, 5);
        Check.that(new int[]{0, 0, 0, 0, 0}).hasLengthBetween(0, 10);
        Check.that(new int[]{0, 0, 0, 0, 0}).hasLengthBetween(
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(new int[]{}).hasLengthBetween(0, 0);
        Check.that(new int[]{}).hasLengthBetween(-1, 1);
        Check.that(new int[]{}).hasLengthBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(new int[]{10, 20, 30}).hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH_BETWEEN,
                false, Exceptions.defaultArgName(), 0, 2, "[10, 20, 30]"),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new int[]{-1, -2, -3}).hasLengthBetween(4,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH_BETWEEN,
                false, Exceptions.defaultArgName(), 4, Integer.MAX_VALUE,
                "[-1, -2, -3]"), thrown.getMessage());
    }
    
    @Test
    public void testHasLengthWhich() {
        Check.that(new int[]{0, 1, 2, 3, 4}).hasLengthWhich().is(5);
        assertEquals("the length of "
                + Config.getConfig().getDefaultArgumentName(), Check.that(
                new int[]{0, 1, 2, 3, 4}).hasLengthWhich().argName);
        assertEquals("the length of the array",
                Check.that(new int[]{0, 1, 2, 3, 4}).named("the array")
                        .hasLengthWhich().argName);
        
        Check.that((int[]) null).isNullOr().hasLengthWhich().is(100);
        
        Exception thrown = null;
        try {
            Check.that((int[]) null).hasLengthWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
}
