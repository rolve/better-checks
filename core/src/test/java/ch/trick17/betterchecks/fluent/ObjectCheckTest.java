package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class ObjectCheckTest {
    
    @Test
    public void testIsNotNull() {
        Check.that(new Object()).isNotNull();
        
        Exception thrown = null;
        try {
            Check.that((Object) null).isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testIsSameAs() {
        Object object = new Object();
        Object other = new Object();
        Check.that(object).isSameAs(object);
        
        Exception thrown = null;
        try {
            Check.that((Object) null).isSameAs(other);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(object).isSameAs(other);
        } catch(Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_SAME_AS, false,
                Exceptions.defaultArgName(), other, object), thrown.getMessage());
    }
    
    @Test
    public void testIsEqualTo() {
        Object object = new String("Hello");
        Check.that(object).isEqualTo(new String("Hello"));
        
        Exception thrown = null;
        try {
            Check.that((Object) null).isEqualTo(Object.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        Object other = new Object();
        try {
            Check.that(object).isEqualTo(other);
        } catch(Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EQUAL_TO, false,
                Exceptions.defaultArgName(), other, object), thrown.getMessage());
    }
    
    @Test
    public void testIsInstanceOf() {
        Check.that(new Object()).isInstanceOf(Object.class);
        Check.that(new Object() {}).isInstanceOf(Object.class);
        Check.that("").isInstanceOf(Object.class);
        Check.that("").isInstanceOf(String.class);
        Check.that("").isInstanceOf(Serializable.class);
        Check.that((Object) null).isNullOr().isInstanceOf(String.class);
        
        Exception thrown = null;
        try {
            Check.that((Object) null).isInstanceOf(Object.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new Object()).isInstanceOf(String.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_INSTANCE_OF, false,
                Exceptions.defaultArgName(), String.class, Object.class),
                thrown.getMessage());
    }
    
    @Test
    public void testHasClass() {
        Check.that(new Object()).hasClass(Object.class);
        Check.that("").hasClass(String.class);
        Check.that((Object) null).isNullOr().hasClass(String.class);
        
        Exception thrown = null;
        try {
            Check.that((Object) null).hasClass(Object.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new Object()).hasClass(String.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CLASS, false,
                Exceptions.defaultArgName(), String.class, Object.class),
                thrown.getMessage());
        
        thrown = null;
        final Object o = new Object() {};
        try {
            Check.that(o).hasClass(Object.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CLASS, false,
                Exceptions.defaultArgName(), Object.class, o.getClass()),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("").hasClass(Object.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CLASS, false,
                Exceptions.defaultArgName(), Object.class, String.class),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("").hasClass(Serializable.class);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_CLASS, false,
                Exceptions.defaultArgName(), Serializable.class, String.class),
                thrown.getMessage());
    }
    
    @Test
    public void testIsNullOr() {
        Check.that(new Object()).isNullOr().isNotNull();
        Check.that((Object) null).isNullOr().isNotNull();
        
        Check.that(new Object()).isNullOr().not().isNotNull();
        Check.that((Object) null).isNullOr().not().isNotNull();
    }
    
    @Test
    public void testNot() {
        final Object object = new Object();
        Check.that((Object) null).not().isNotNull();
        Check.that(object).not().not().isNotNull();
        Check.that((Object) null).not().not().not().isNotNull();
        
        Check.that("").not().isNotEmpty();
        Check.that("hello").not().not().isNotEmpty();
        Check.that("hello").not().startsWith("bye");
        
        Check.that("hello").isNullOr().not().startsWith("hi");
        Check.that((String) null).isNullOr().not().startsWith("bye");
        Check.that((String) null).not().isNullOr().startsWith("bye");
        
        Exception thrown = null;
        try {
            Check.that(object).not().isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, true,
                Exceptions.defaultArgName(), object), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String) null).not().startsWith("hi");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("hi").not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "hi"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that("hi").isNullOr().not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, true,
                Exceptions.defaultArgName(), "hi"), thrown.getMessage());
    }
    
    @Test
    public void testNamed() {
        Exception thrown = null;
        try {
            Check.that((Object) null).named("my arg").isNotNull();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions
                .formatMsg(MessageType.ARG_NULL, false, "my arg"), thrown
                .getMessage());
    }
}
