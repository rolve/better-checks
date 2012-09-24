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
            Check.that((Number) null).isNegative();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
}
