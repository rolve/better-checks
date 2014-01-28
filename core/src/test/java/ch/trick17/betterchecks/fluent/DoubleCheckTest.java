package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class DoubleCheckTest {
    
    @Test
    public void testIsPositive() {
        Check.that(1.5).isPositive();
        Check.that(2.5).isPositive();
        Check.that(Double.POSITIVE_INFINITY).isPositive();
        Check.that(Double.MAX_VALUE).isPositive();
        Check.that(Double.MIN_NORMAL).isPositive();
        Check.that(Double.MIN_VALUE).isPositive();
        
        Check.that(0.0).not().isPositive();
        Check.that(-1.5).not().isPositive();
        Check.that(Double.NEGATIVE_INFINITY).not().isPositive();
        
        Exception thrown = null;
        try {
            Check.that(0.0).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), 0.0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(-1.5).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), -1.5), thrown.getMessage());
    }
    
    @Test
    public void testIsNegative() {
        Check.that(-1.5).isNegative();
        Check.that(-2.5).isNegative();
        Check.that(Double.NEGATIVE_INFINITY).isNegative();
        
        Check.that(0.0).not().isNegative();
        Check.that(1.5).not().isNegative();
        Check.that(Double.POSITIVE_INFINITY).not().isNegative();
        
        Exception thrown = null;
        try {
            Check.that(0.0).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 0.0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(1.5).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 1.5), thrown.getMessage());
    }
    
    @Test
    public void testIs() {
        Check.that(1.5).is(1.5);
        Check.that(-2.0).is(-2);
        Check.that(0.0).is(0);
        Check.that(Double.POSITIVE_INFINITY).is(Double.POSITIVE_INFINITY);
        
        Check.that(1.5).not().is(2);
        Check.that(-2.5).not().is(1);
        Check.that(0.0).not().is(1);
        Check.that(Double.NEGATIVE_INFINITY).not().is(Double.POSITIVE_INFINITY);
        
        Exception thrown = null;
        try {
            Check.that(0.0).is(1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_IS, false, Exceptions
                .defaultArgName(), 1.0, 0.0), thrown.getMessage());
    }
    
    @Test
    public void testIsGreaterThan() {
        Check.that(1.5).isGreaterThan(0);
        Check.that(-2.5).isGreaterThan(-3);
        Check.that(0.0).isGreaterThan(-1);
        Check.that(Double.POSITIVE_INFINITY).isGreaterThan(
                Double.NEGATIVE_INFINITY);
        
        Check.that(1.5).not().isGreaterThan(2);
        Check.that(-2.5).not().isGreaterThan(1);
        Check.that(0.0).not().isGreaterThan(0);
        Check.that(Double.NEGATIVE_INFINITY).not().isGreaterThan(
                Double.POSITIVE_INFINITY);
        
        Exception thrown = null;
        try {
            Check.that(0.0).isGreaterThan(1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 1.0, 0.0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0.0).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 0.0, 0.0), thrown.getMessage());
    }
    
    @Test
    public void testIsLessThan() {
        Check.that(1.5).isLessThan(2);
        Check.that(-2.5).isLessThan(1);
        Check.that(0.0).isLessThan(1);
        Check.that(Double.NEGATIVE_INFINITY).isLessThan(
                Double.POSITIVE_INFINITY);
        
        Check.that(1.5).not().isLessThan(0);
        Check.that(-2.5).not().isLessThan(-3);
        Check.that(0.0).not().isLessThan(0);
        Check.that(Double.POSITIVE_INFINITY).not().isLessThan(
                Double.NEGATIVE_INFINITY);
        
        Exception thrown = null;
        try {
            Check.that(1.5).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0.0, 1.5), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0.0).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0.0, 0.0), thrown.getMessage());
    }
    
    @Test
    public void testIsBetween() {
        Check.that(1.5).isBetween(0, 2);
        Check.that(-1.9).isBetween(-2, 1);
        Check.that(0.0).isBetween(0, 0);
        Check.that(0.0).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(1.5).not().isBetween(-1, 0);
        Check.that(-2.5).not().isBetween(-1, 0);
        Check.that(Double.MAX_VALUE).not().isBetween(Double.NEGATIVE_INFINITY,
                Double.MAX_VALUE * 0.9);
        
        Exception thrown = null;
        try {
            Check.that(2.5).isBetween(0, 1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 0.0, 1.0, 2.5), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(0.0).isBetween(0.5, 1);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 0.5, 1.0, 0.0), thrown
                .getMessage());
    }
    
    @Test
    public void testIsNotNaN() {
        Check.that(0.0).isNotNaN();
        Check.that(1.1).isNotNaN();
        Check.that(Double.POSITIVE_INFINITY).isNotNaN();
        Check.that(Double.MIN_VALUE).isNotNaN();
        
        Check.that(Double.NaN).not().isNotNaN();
        
        Exception thrown = null;
        try {
            Check.that(Double.NaN).isNotNaN();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NAN, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(Float.NaN).isNotNaN();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NAN, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(0.0).not().isNotNaN();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NAN, true, Exceptions
                .defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsNotInfinite() {
        Check.that(0.0).isNotInfinite();
        Check.that(1.1).isNotInfinite();
        Check.that(Double.MAX_VALUE).isNotInfinite();
        Check.that(Double.NaN).isNotInfinite();
        
        Check.that(Double.POSITIVE_INFINITY).not().isNotInfinite();
        Check.that(Double.NEGATIVE_INFINITY).not().isNotInfinite();
        
        Exception thrown = null;
        try {
            Check.that(Double.POSITIVE_INFINITY).isNotInfinite();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INFINITE, false,
                Exceptions.defaultArgName(), Double.POSITIVE_INFINITY), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(Double.NEGATIVE_INFINITY).isNotInfinite();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INFINITE, false,
                Exceptions.defaultArgName(), Double.POSITIVE_INFINITY), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(Float.POSITIVE_INFINITY).isNotInfinite();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INFINITE, false,
                Exceptions.defaultArgName(), Double.POSITIVE_INFINITY), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(Float.NEGATIVE_INFINITY).isNotInfinite();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INFINITE, false,
                Exceptions.defaultArgName(), Double.POSITIVE_INFINITY), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(0.0).not().isNotInfinite();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INFINITE, true,
                Exceptions.defaultArgName(), 0.0), thrown.getMessage());
    }
}
