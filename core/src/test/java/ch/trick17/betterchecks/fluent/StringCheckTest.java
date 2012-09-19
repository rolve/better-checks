package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.MsgFormatId;
import ch.trick17.betterchecks.MsgFormatter;

public class StringCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotEmpty() {
        Check.that("bla").isNotEmpty();
        Check.that(" ").isNotEmpty();
        Check.that("bla").isNullOr().isNotEmpty();
        Check.that((String) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that("").isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_EMPTY, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_NULL, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotWhitespace() {
        Check.that("hello").isNotWhitespace();
        Check.that(" bla  ").isNotWhitespace();
        
        Exception thrown = null;
        try {
            Check.that(" \n\n\t      \r\n ").isNotWhitespace();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_WHITESPACE,
                Config.getConfig().getDefaultArgumentName()), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that("").isNotWhitespace();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_WHITESPACE,
                Config.getConfig().getDefaultArgumentName()), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLength() {
        Check.that("hello").hasLength(5);
        Check.that("  ").hasLength(2);
        Check.that("").hasLength(0);
        
        Exception thrown = null;
        try {
            Check.that("bla").hasLength(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH, Config
                .getConfig().getDefaultArgumentName(), 2, "bla"), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLengthBetween() {
        Check.that("hello").hasLengthBetween(5, 5);
        Check.that("hello").hasLengthBetween(0, 10);
        Check.that("hello").hasLengthBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Check.that("").hasLengthBetween(0, 0);
        Check.that("").hasLengthBetween(-1, 1);
        Check.that("").hasLengthBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that("bla").hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN,
                Config.getConfig().getDefaultArgumentName(), 0, 2, "bla"),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("bla").hasLengthBetween(4, Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN,
                Config.getConfig().getDefaultArgumentName(), 4,
                Integer.MAX_VALUE, "bla"), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testStartsWith() {
        Check.that("hello").startsWith("h");
        Check.that("hello").startsWith("");
        Check.that("hello").startsWith("hello");
        
        Exception thrown = null;
        try {
            Check.that("hello").startsWith("x");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_STARTS, Config
                .getConfig().getDefaultArgumentName(), "x", "hello"), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testEndsWith() {
        Check.that("hello").endsWith("o");
        Check.that("hello").endsWith("");
        Check.that("hello").endsWith("hello");
        
        Exception thrown = null;
        try {
            Check.that("hello").endsWith("x");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_ENDS, Config
                .getConfig().getDefaultArgumentName(), "x", "hello"), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testMatches() {
        Check.that("hello").matches("hello");
        Check.that("hello").matches("h.*");
        
        Exception thrown = null;
        try {
            Check.that("hello").matches("hi");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_MATCHES, Config
                .getConfig().getDefaultArgumentName(), "hi", "hello"), thrown
                .getMessage());
    }
}
