package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.regex.Pattern;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class StringCheckTest {
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("abc").not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "abc"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_WHITESPACE, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("").isNotWhitespace();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_WHITESPACE, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).isNotWhitespace();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH, false,
                Exceptions.defaultArgName(), 2, "bla"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).hasLength(0);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_LENGTH_BETWEEN,
                false, Exceptions.defaultArgName(), 0, 2, "bla"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that("bla").hasLengthBetween(4, Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions
                .formatMsg(MessageType.ARG_LENGTH_BETWEEN, false, Exceptions
                        .defaultArgName(), 4, Integer.MAX_VALUE, "bla"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).hasLengthBetween(Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasLengthWhich() {
        Check.that("hello").hasLengthWhich().is(5);
        assertEquals("the length of "
                + Config.getConfig().getDefaultArgumentName(), Check.that(
                "hello").hasLengthWhich().argName);
        assertEquals("the length of string", Check.that("hello")
                .named("string").hasLengthWhich().argName);
        
        Check.that("hello").isNullOr().hasLengthWhich().is(5);
        Check.that((String) null).isNullOr().hasLengthWhich().is(100);
        
        Exception thrown = null;
        try {
            Check.that((String) null).hasLengthWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_STARTS, false,
                Exceptions.defaultArgName(), "x", "hello"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).startsWith("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_ENDS, false,
                Exceptions.defaultArgName(), "x", "hello"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).endsWith("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContains() {
        Check.that("hello").contains("o");
        Check.that("hello").contains("");
        Check.that("hello").contains("hello");
        
        Exception thrown = null;
        try {
            Check.that("hello").contains("x");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CONTAINS, false,
                Exceptions.defaultArgName(), "x", "hello"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).contains("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContainsAny() {
        Check.that("hello").containsAny("o");
        Check.that("hello").containsAny("o", "x", "y");
        Check.that("hello").containsAny("hello", "x", "y");
        Check.that("hello").containsAny("");
        Check.that("hello").containsAny(new StringBuilder("h"));
        
        Exception thrown = null;
        try {
            Check.that("hello").containsAny("x", "y");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CONTAINS_ANY, false,
                Exceptions.defaultArgName(), "[x, y]", "hello"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).containsAny("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContainsAll() {
        Check.that("hello").containsAll("o");
        Check.that("hello").containsAll("o", "h", "e");
        Check.that("hello").containsAll("hello", "ello", "ll");
        Check.that("hello").containsAll("");
        Check.that("hello").containsAll(new StringBuilder("h"));
        Check.that("hello").containsAll();
        
        Exception thrown = null;
        try {
            Check.that("hello").containsAll("h", "y");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CONTAINS_ALL, false,
                Exceptions.defaultArgName(), "[h, y]", "hello"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).containsAll("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
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
        assertEquals(Exceptions.formatMsg(MessageType.ARG_MATCHES, false,
                Exceptions.defaultArgName(), "hi", "hello"), thrown
                .getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).matches(".*");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testMatchesPattern() {
        Check.that("hello").matches(Pattern.compile("hello"));
        Check.that("hello").matches(Pattern.compile("h.*"));
        
        Exception thrown = null;
        try {
            Check.that("hello").matches(Pattern.compile("hi"));
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_MATCHES, false,
                Exceptions.defaultArgName(), Pattern.compile("hi"), "hello"),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).matches(Pattern.compile(".*"));
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIs() {
        Check.that("hello").is("hello");
        Check.that("").is("");
        
        Exception thrown = null;
        try {
            Check.that("hello").is("hella");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_IS, false, Exceptions
                .defaultArgName(), "hella", "hello"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).is("");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsUrl() {
        Check.that("http://").isUrl();
        Check.that("https://example").isUrl();
        Check.that("file://file").isUrl();
        Check.that("ftp://me:pw@example.com:8080/some%20path/?and-a-query=1")
                .isUrl();
        
        Exception thrown = null;
        try {
            Check.that("").isUrl();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_URL, false,
                Exceptions.defaultArgName(), ""), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof MalformedURLException);
        
        thrown = null;
        try {
            Check.that("nonexistingprotocol://example.com").isUrl();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_URL, false,
                Exceptions.defaultArgName(),
                "nonexistingprotocol://example.com"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof MalformedURLException);
        
        thrown = null;
        try {
            Check.that((String) null).isUrl();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsUrlWhich() {
        Check.that("https://example").isUrlWhich().hasProtocol("https")
                .hasHostWhich().is("example");
        assertEquals(Config.getConfig().getDefaultArgumentName(), Check.that(
                "https://example").isUrlWhich().argName);
        assertEquals("url", Check.that("https://example").named("url")
                .isUrlWhich().argName);
        
        Exception thrown = null;
        try {
            Check.that("invalid").isUrlWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_URL, false,
                Exceptions.defaultArgName(), "invalid"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof MalformedURLException);
        
        thrown = null;
        try {
            Check.that("invalid").named("string").isUrlWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_URL, false, "string",
                "invalid"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof MalformedURLException);
        
        Check.that((String) null).isNullOr().isUrlWhich();
        Check.that((String) null).isNullOr().isUrlWhich().hasProtocol("http");
        
        thrown = null;
        try {
            Check.that((String) null).isUrlWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsInt() {
        Check.that("42").isInt();
        Check.that("0").isInt();
        Check.that("1").isInt();
        Check.that("-1").isInt();
        Check.that(String.valueOf(Integer.MAX_VALUE)).isInt();
        Check.that(String.valueOf(Integer.MIN_VALUE)).isInt();
        
        Exception thrown = null;
        try {
            Check.that("invalid").isInt();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INT, false,
                Exceptions.defaultArgName(), "invalid"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
        
        thrown = null;
        try {
            Check.that("").isInt();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INT, false,
                Exceptions.defaultArgName(), ""), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
        
        thrown = null;
        try {
            Check.that(Integer.MAX_VALUE + "0").isInt();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INT, false,
                Exceptions.defaultArgName(), Integer.MAX_VALUE + "0"), thrown
                .getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
        
        Check.that((String) null).isNullOr().isInt();
        
        thrown = null;
        try {
            Check.that((String) null).isInt();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsIntWhich() {
        Check.that("42").isIntWhich().is(42);
        assertEquals(Config.getConfig().getDefaultArgumentName(), Check.that(
                "42").isIntWhich().argName);
        assertEquals("number",
                Check.that("42").named("number").isIntWhich().argName);
        
        Exception thrown = null;
        try {
            Check.that("invalid").isIntWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INT, false,
                Exceptions.defaultArgName(), "invalid"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
        
        thrown = null;
        try {
            Check.that("invalid").named("string").isIntWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INT, false, "string",
                "invalid"), thrown.getMessage());
        assertTrue(thrown.getCause() instanceof NumberFormatException);
        
        Check.that((String) null).isNullOr().isIntWhich();
        Check.that((String) null).isNullOr().isIntWhich().is(0);
        
        thrown = null;
        try {
            Check.that((String) null).isIntWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
}
