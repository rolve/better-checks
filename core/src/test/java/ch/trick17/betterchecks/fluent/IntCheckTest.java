package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class IntCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsPositive() {
        Check.that(1).isPositive();
        Check.that(2).isPositive();
        Check.that(Integer.MAX_VALUE).isPositive();
        
        Check.that(0).not().isPositive();
        Check.that(-1).not().isPositive();
        Check.that(Integer.MIN_VALUE).not().isPositive();
        
        Exception thrown = null;
        try {
            Check.that(0).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(-1).isPositive();
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
        Check.that(-1).isNegative();
        Check.that(-2).isNegative();
        Check.that(Integer.MIN_VALUE).isNegative();
        
        Check.that(0).not().isNegative();
        Check.that(1).not().isNegative();
        Check.that(Integer.MAX_VALUE).not().isNegative();
        
        Exception thrown = null;
        try {
            Check.that(0).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(1).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), -1), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsEqualTo() {
        Check.that(1).isEqualTo(1);
        Check.that(-2).isEqualTo(-2);
        Check.that(0).isEqualTo(0);
        Check.that(Integer.MIN_VALUE).isEqualTo(Integer.MIN_VALUE);
        
        Check.that(1).not().isEqualTo(2);
        Check.that(-2).not().isEqualTo(1);
        Check.that(0).not().isEqualTo(1);
        Check.that(Integer.MIN_VALUE).not().isEqualTo(Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(0).isEqualTo(1);
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
        Check.that(1).isGreaterThan(0);
        Check.that(-2).isGreaterThan(-3);
        Check.that(0).isGreaterThan(-1);
        Check.that(Integer.MAX_VALUE).isGreaterThan(Integer.MIN_VALUE);
        
        Check.that(1).not().isGreaterThan(2);
        Check.that(-2).not().isGreaterThan(1);
        Check.that(0).not().isGreaterThan(0);
        Check.that(Integer.MIN_VALUE).not().isGreaterThan(Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(0).isGreaterThan(1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 1, 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0).isGreaterThan(0);
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
        Check.that(1).isLessThan(2);
        Check.that(-2).isLessThan(1);
        Check.that(0).isLessThan(1);
        Check.that(Integer.MIN_VALUE).isLessThan(Integer.MAX_VALUE);
        
        Check.that(1).not().isLessThan(0);
        Check.that(-2).not().isLessThan(-3);
        Check.that(0).not().isLessThan(0);
        Check.that(Integer.MAX_VALUE).not().isLessThan(Integer.MIN_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(1).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0, 1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0).isLessThan(0);
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
        Check.that(1).isBetween(0, 2);
        Check.that(-2).isBetween(-2, 1);
        Check.that(0).isBetween(0, 0);
        Check.that(0).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(1).not().isBetween(-1, 0);
        Check.that(-2).not().isBetween(-1, 0);
        Check.that(Integer.MAX_VALUE).not().isBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE - 1);
        
        Exception thrown = null;
        try {
            Check.that(2).isBetween(0, 1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 0, 1, 2), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0).isBetween(1, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 1, 2, 0), thrown.getMessage());
    }
}
