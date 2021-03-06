package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class ObjectArrayCheckTest {
    
    @Test
    public void testIsNotEmpty() {
        Check.that(new String[]{"", ""}).isNotEmpty();
        Check.that(new String[]{""}).isNotEmpty();
        Check.that(new String[]{"", ""}).isNullOr().isNotEmpty();
        Check.that((String) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(new String[]{}).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new Object[]{"a"}).not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "[a]"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String[]) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasLength() {
        Check.that(new String[]{"", "", "", "", ""}).hasLength(5);
        Check.that(new String[]{"", ""}).hasLength(2);
        Check.that(new String[]{}).hasLength(0);
        
        Exception thrown = null;
        try {
            Check.that(new String[]{"A", "B", "C"}).hasLength(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH, false,
                Exceptions.defaultArgName(), 2, "[A, B, C]"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((String[]) null).hasLength(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasLengthBetween() {
        Check.that(new String[]{"", "", "", "", ""}).hasLengthBetween(5, 5);
        Check.that(new String[]{"", "", "", "", ""}).hasLengthBetween(0, 10);
        Check.that(new String[]{"", "", "", "", ""}).hasLengthBetween(
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(new String[]{}).hasLengthBetween(0, 0);
        Check.that(new String[]{}).hasLengthBetween(-1, 1);
        Check.that(new String[]{}).hasLengthBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(new String[]{"a", "b", "c"}).hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH_BETWEEN,
                false, Exceptions.defaultArgName(), 0, 2, "[a, b, c]"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that(new String[]{"1", "2", "3"}).hasLengthBetween(4,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH_BETWEEN,
                false, Exceptions.defaultArgName(), 4, Integer.MAX_VALUE,
                "[1, 2, 3]"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String[]) null).hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasLengthWhich() {
        Check.that(new Object[]{0, 1, 2, 3, 4}).hasLengthWhich().is(5);
        assertEquals("the length of "
                + Config.getConfig().getDefaultArgumentName(), Check.that(
                new Object[]{0, 1, 2, 3, 4}).hasLengthWhich().argName);
        assertEquals("the length of the array", Check.that(
                new Object[]{0, 1, 2, 3, 4}).named("the array")
                .hasLengthWhich().argName);
        
        Check.that((Object[]) null).isNullOr().hasLengthWhich().is(100);
        
        Exception thrown = null;
        try {
            Check.that((Object[]) null).hasLengthWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContainsNoNull() {
        Check.that(new Object[]{"hello", "world"}).containsNoNull();
        Check.that(new Object[]{"hello"}).containsNoNull();
        Check.that(new Object[]{}).containsNoNull();
        
        Check.that((Object[]) null).isNullOr().containsNoNull();
        
        Exception thrown = null;
        try {
            Check.that(new String[]{"a", "b", null}).containsNoNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CONTAINS_NULL, false,
                Exceptions.defaultArgName(), "[a, b, null]"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((Object[]) null).containsNoNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
}
