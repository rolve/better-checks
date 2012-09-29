package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.InvalidCheckException;
import ch.trick17.betterchecks.MessageType;

public class LongCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsPositive() {
        Check.that(1L).isPositive();
        Check.that(2L).isPositive();
        Check.that(Long.MAX_VALUE).isPositive();
        
        Check.that(0L).not().isPositive();
        Check.that(-1L).not().isPositive();
        Check.that(Long.MIN_VALUE).not().isPositive();
        
        Exception thrown = null;
        try {
            Check.that(0L).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(-1L).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), -1), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsNegative() {
        Check.that(-1L).isNegative();
        Check.that(-2L).isNegative();
        Check.that(Long.MIN_VALUE).isNegative();
        
        Check.that(0L).not().isNegative();
        Check.that(1L).not().isNegative();
        Check.that(Long.MAX_VALUE).not().isNegative();
        
        Exception thrown = null;
        try {
            Check.that(0L).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(1L).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 1), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsEqualTo() {
        Check.that(1L).isEqualTo(1);
        Check.that(-2L).isEqualTo(-2);
        Check.that(0L).isEqualTo(0);
        Check.that(Long.MIN_VALUE).isEqualTo(Long.MIN_VALUE);
        
        Check.that(1L).not().isEqualTo(2);
        Check.that(-2L).not().isEqualTo(1);
        Check.that(0L).not().isEqualTo(1);
        Check.that(Long.MIN_VALUE).not().isEqualTo(Long.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(0L).isEqualTo(1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EQUAL, false,
                Exceptions.defaultArgName(), 1, 0), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsGreaterThan() {
        Check.that(1L).isGreaterThan(0);
        Check.that(-2L).isGreaterThan(-3);
        Check.that(0L).isGreaterThan(-1);
        Check.that(Long.MAX_VALUE).isGreaterThan(Long.MIN_VALUE);
        
        Check.that(1L).not().isGreaterThan(2);
        Check.that(-2L).not().isGreaterThan(1);
        Check.that(0L).not().isGreaterThan(0);
        Check.that(Long.MIN_VALUE).not().isGreaterThan(Long.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(0L).isGreaterThan(1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 1, 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0L).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 0, 0), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsLessThan() {
        Check.that(1L).isLessThan(2);
        Check.that(-2L).isLessThan(1);
        Check.that(0L).isLessThan(1);
        Check.that(Long.MIN_VALUE).isLessThan(Long.MAX_VALUE);
        
        Check.that(1L).not().isLessThan(0);
        Check.that(-2L).not().isLessThan(-3);
        Check.that(0L).not().isLessThan(0);
        Check.that(Long.MAX_VALUE).not().isLessThan(Long.MIN_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(1L).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0, 1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0L).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0, 0), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsBetween() {
        Check.that(1L).isBetween(0, 2);
        Check.that(-2L).isBetween(-2, 1);
        Check.that(0L).isBetween(0, 0);
        Check.that(0L).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(1L).not().isBetween(-1, 0);
        Check.that(-2L).not().isBetween(-1, 0);
        Check.that(Long.MAX_VALUE).not().isBetween(Long.MIN_VALUE,
                Long.MAX_VALUE - 1);
        
        Exception thrown = null;
        try {
            Check.that(2L).isBetween(0, 1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 0, 1, 2), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0L).isBetween(1, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 1, 2, 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(-1L).isBetween(2, 1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof InvalidCheckException);
        assertEquals("min (2) must be less than or equal to max (1)", thrown
                .getMessage());
    }
}
