package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class NumberCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsPositive() {
        Check.that(1).isPositive();
        Check.that(Integer.MAX_VALUE).isPositive();
        Check.that(new Integer(1)).isPositive();
        
        Check.that((long) 1).isPositive();
        Check.that((short) 1).isPositive();
        Check.that((byte) 1).isPositive();
        
        Check.that(1.0).isPositive();
        Check.that(Double.MIN_VALUE).isPositive();
        Check.that(Double.MAX_VALUE).isPositive();
        Check.that(Double.POSITIVE_INFINITY).isPositive();
        
        Check.that(new AtomicInteger(1)).isPositive();
        Check.that(
                new BigInteger(
                        "999999999999999999999999999999999999999999999999999999"))
                .isPositive();
        
        // This is a tricky one: it is not handled correctly with .doubleValue()
        final StringBuilder smallNumber = new StringBuilder("0.");
        for(int i = 0; i < Short.MAX_VALUE; i++)
            smallNumber.append('0');
        smallNumber.append('1');
        Check.that(new BigDecimal(smallNumber.toString())).isPositive();
        
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
        
        thrown = null;
        try {
            Check.that(new BigDecimal(-1)).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_POSITIVE, false,
                Exceptions.defaultArgName(), -1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Number) null).isPositive();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsNegative() {
        Check.that(-1).isNegative();
        Check.that(Integer.MIN_VALUE).isNegative();
        Check.that(new Integer(-1)).isNegative();
        
        Check.that((long) -1).isNegative();
        Check.that((short) -1).isNegative();
        Check.that((byte) -1).isNegative();
        
        Check.that(-1.0).isNegative();
        Check.that(-Double.MIN_VALUE).isNegative();
        Check.that(-Double.MAX_VALUE).isNegative();
        Check.that(Double.NEGATIVE_INFINITY).isNegative();
        
        Check.that(new AtomicInteger(-1)).isNegative();
        Check.that(
                new BigInteger(
                        "-999999999999999999999999999999999999999999999999999999"))
                .isNegative();
        
        // This is a tricky one: it is not handled correctly with .doubleValue()
        final StringBuilder smallNumber = new StringBuilder("-0.");
        for(int i = 0; i < Short.MAX_VALUE; i++)
            smallNumber.append('0');
        smallNumber.append('1');
        Check.that(new BigDecimal(smallNumber.toString())).isNegative();
        
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
                Exceptions.defaultArgName(), 1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new BigDecimal(1)).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NEGATIVE, false,
                Exceptions.defaultArgName(), 1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Number) null).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsEqualTo() {
        Check.that(1).isEqualTo(1);
        Check.that(1).isEqualTo(1.0);
        Check.that(0).isEqualTo(new BigDecimal("0"));
        
        Exception thrown = null;
        try {
            Check.that(-1).isEqualTo(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EQUAL, false,
                Exceptions.defaultArgName(), 0, -1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Number) null).isEqualTo(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsGreaterThan() {
        Check.that(1).isGreaterThan(0);
        Check.that(1).isGreaterThan(0.5);
        Check.that(Integer.MAX_VALUE).isGreaterThan(Integer.MAX_VALUE - 1);
        Check.that(new Integer(1)).isGreaterThan(new BigDecimal(-1));
        Check.that(Double.POSITIVE_INFINITY).isGreaterThan(0);
        
        Check.that((long) 2).isGreaterThan(1);
        Check.that((short) 2).isGreaterThan(1);
        Check.that((byte) 2).isGreaterThan(1);
        Check.that((float) 2).isGreaterThan(1);
        
        Check.that(2.0).isGreaterThan(1.99999);
        Check.that(Double.MIN_VALUE).isGreaterThan(0);
        Check.that(Double.MAX_VALUE).isGreaterThan(0);
        Check.that(Double.POSITIVE_INFINITY).isGreaterThan(0);
        
        Check.that(new AtomicInteger(1)).isGreaterThan(0);
        Check.that(
                new BigInteger(
                        "999999999999999999999999999999999999999999999999999999"))
                .isGreaterThan(0);
        
        // This is a tricky one: it is not handled correctly with .doubleValue()
        final StringBuilder smallNumber = new StringBuilder("0.");
        for(int i = 0; i < Short.MAX_VALUE; i++)
            smallNumber.append('0');
        smallNumber.append('1');
        Check.that(new BigDecimal(smallNumber.toString())).isGreaterThan(0);
        
        Exception thrown = null;
        try {
            Check.that(0).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 0, 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(-1).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_GREATER, false,
                Exceptions.defaultArgName(), 0, -1), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Number) null).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new WeirdNumber()).isGreaterThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof RuntimeException);
        assertEquals(
                "The given number (unparsable of class ch.trick17.betterchecks.fluent.NumberCheckTest$WeirdNumber) does not have a parsable string representation",
                thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsLessThan() {
        Check.that(-1).isLessThan(0);
        Check.that(Integer.MIN_VALUE).isLessThan(0);
        Check.that(new Integer(-1)).isLessThan(0);
        
        Check.that((long) -1).isLessThan(0);
        Check.that((short) -1).isLessThan(0);
        Check.that((byte) -1).isLessThan(0);
        Check.that((float) -1).isLessThan(0);
        
        Check.that(-1.0).isLessThan(0);
        Check.that(-Double.MIN_VALUE).isLessThan(0);
        Check.that(-Double.MAX_VALUE).isLessThan(0);
        Check.that(Double.NEGATIVE_INFINITY).isLessThan(0);
        
        Check.that(new AtomicInteger(-1)).isLessThan(0);
        Check.that(
                new BigInteger(
                        "-999999999999999999999999999999999999999999999999999999"))
                .isLessThan(0);
        
        // This is a tricky one: it is not handled correctly with .doubleValue()
        final StringBuilder smallNumber = new StringBuilder("-0.");
        for(int i = 0; i < Short.MAX_VALUE; i++)
            smallNumber.append('0');
        smallNumber.append('1');
        Check.that(new BigDecimal(smallNumber.toString())).isLessThan(0);
        
        Exception thrown = null;
        try {
            Check.that(0).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LESS, false,
                Exceptions.defaultArgName(), 0, 0), thrown.getMessage());
        
        thrown = null;
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
            Check.that((Number) null).isLessThan(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsBetween() {
        Check.that(0).isBetween(-1, 1);
        Check.that(0).isBetween(0, 0);
        Check.that(0.1f).isBetween(Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        
        Exception thrown = null;
        try {
            Check.that(0).isBetween(1, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 1, 2, 0), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(3).isBetween(1, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_BETWEEN, false,
                Exceptions.defaultArgName(), 1, 2, 3), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Number) null).isBetween(Double.NEGATIVE_INFINITY,
                    Double.POSITIVE_INFINITY);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
    }
    
    @SuppressWarnings("serial")
    private static class WeirdNumber extends Number {
        
        @Override
        public int intValue() {
            return 0;
        }
        
        @Override
        public long longValue() {
            return 0;
        }
        
        @Override
        public float floatValue() {
            return 0;
        }
        
        @Override
        public double doubleValue() {
            return 0;
        }
        
        @Override
        public String toString() {
            return "unparsable";
        }
    }
}
