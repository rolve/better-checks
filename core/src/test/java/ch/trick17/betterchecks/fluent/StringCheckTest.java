package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.MsgFormatter;
import ch.trick17.betterchecks.MessageFormatId;

public class StringCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotEmpty() {
        Check.that("bla").isNotEmpty();
        Check.that(" ").isNotEmpty();
        Check.that("bla").isNullOr().isNotEmpty();
        Check.that(null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that("").isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_EMPTY, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_NULL, Config
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_WHITESPACE,
                Config.getConfig().getDefaultArgumentName()), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that("").isNotWhitespace();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_WHITESPACE,
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_LENGTH,
                Config.getConfig().getDefaultArgumentName(), 2, "bla"), thrown
                .getMessage());
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_STARTS,
                Config.getConfig().getDefaultArgumentName(), "x", "hello"),
                thrown.getMessage());
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_ENDS, Config
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_MATCHES,
                Config.getConfig().getDefaultArgumentName(), "hi", "hello"),
                thrown.getMessage());
    }
}
