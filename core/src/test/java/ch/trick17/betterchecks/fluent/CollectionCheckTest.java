package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class CollectionCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotEmpty() {
        Check.that(Arrays.asList(1)).isNotEmpty();
        Check.that(Arrays.asList(1, 2, 3)).isNotEmpty();
        Check.that(Arrays.asList(1)).isNullOr().isNotEmpty();
        Check.that((Collection<?>) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(Arrays.asList()).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(Arrays.asList("a")).not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "[a]"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Collection<?>) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasSize() {
        Check.that(Arrays.asList(1, 2, 3, 4, 5)).hasSize(5);
        Check.that(Arrays.asList(1, 1)).hasSize(2);
        Check.that(Arrays.asList()).hasSize(0);
        
        Exception thrown = null;
        try {
            Check.that(Arrays.asList(1)).hasSize(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_SIZE, false,
                Exceptions.defaultArgName(), 2, Arrays.asList(1)), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasSizeBetween() {
        Check.that(Arrays.asList(1, 2, 3, 4, 5)).hasSizeBetween(5, 5);
        Check.that(Arrays.asList(1, 2, 3, 4, 5)).hasSizeBetween(0, 10);
        Check.that(Arrays.asList(1, 2, 3, 4, 5)).hasSizeBetween(
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(Arrays.asList()).hasSizeBetween(0, 0);
        Check.that(Arrays.asList()).hasSizeBetween(-1, 1);
        Check.that(Arrays.asList()).hasSizeBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(Arrays.asList(1, 2, 3)).hasSizeBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, false,
                Exceptions.defaultArgName(), 0, 2, Arrays.asList(1, 2, 3)),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(Arrays.asList(1, 2, 3)).hasSizeBetween(4,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, false,
                Exceptions.defaultArgName(), 4, Integer.MAX_VALUE, Arrays
                        .asList(1, 2, 3)), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasSizeWhich() {
        Check.that(Arrays.asList(1, 2, 3, 4, 5)).hasSizeWhich().is(5);
        assertEquals("the size of "
                + Config.getConfig().getDefaultArgumentName(), Check.that(
                Arrays.asList(1, 2, 3, 4, 5)).hasSizeWhich().argName);
        assertEquals("the size of the list",
                Check.that(Arrays.asList(1, 2, 3, 4, 5)).named("the list")
                        .hasSizeWhich().argName);
        
        Check.that((Collection<?>) null).isNullOr().hasSizeWhich().is(100);
        
        Exception thrown = null;
        try {
            Check.that((Collection<?>) null).hasSizeWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testContainsNoNull() {
        Check.that(Arrays.asList("hello", "world")).containsNoNull();
        Check.that(Arrays.asList("hello")).containsNoNull();
        Check.that(Arrays.asList()).containsNoNull();
        
        Check.that((Collection<?>) null).isNullOr().containsNoNull();
        
        Exception thrown = null;
        try {
            Check.that(Arrays.asList("a", "b", null)).containsNoNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CONTAINS_NULL, false,
                Exceptions.defaultArgName(), "[a, b, null]"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((Collection<?>) null).containsNoNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
}
