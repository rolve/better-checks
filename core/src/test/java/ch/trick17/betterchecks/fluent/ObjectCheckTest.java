package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MsgFormatId;

public class ObjectCheckTest {
    
    @Test
    public void testIsNullOr() {
        Check.that(new Object()).isNullOr().isNotNull();
        Check.that((Object) null).isNullOr().isNotNull();
        
        Check.that(new Object()).isNullOr().not().isNotNull();
        Check.that((Object) null).isNullOr().not().isNotNull();
    }
    
    @Test
    @SuppressWarnings("null")
    public void testNot() {
        Check.that((Object) null).not().isNotNull();
        Check.that(new Object()).not().not().isNotNull();
        Check.that((Object) null).not().not().not().isNotNull();
        
        Check.that("").not().isNotEmpty();
        Check.that("hello").not().not().isNotEmpty();
        Check.that("hello").not().startsWith("bye");
        
        Check.that("hello").isNullOr().not().startsWith("hi");
        Check.that((String) null).isNullOr().not().startsWith("bye");
        Check.that((String) null).not().isNullOr().startsWith("bye");
        
        Exception thrown = null;
        try {
            Check.that(new Object()).not().isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_NULL, true,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).not().startsWith("hi");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_NULL, true,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("hi").not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_EMPTY, true,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("hi").isNullOr().not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_EMPTY, true,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotNull() {
        Check.that(new Object()).isNotNull();
        
        Exception thrown = null;
        try {
            Check.that((Object) null).isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MsgFormatId.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testNamed() {
        Exception thrown = null;
        try {
            Check.that((Object) null).named("my arg").isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions
                .formatMsg(MsgFormatId.ARG_NULL, false, "my arg"), thrown
                .getMessage());
    }
}
