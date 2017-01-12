package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static ch.trick17.betterchecks.Exceptions.formatMsg;
import static ch.trick17.betterchecks.MessageType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;

public class MapCheckTest {
    
    private static Map<?, ?> asMap(Object... keysAndValues) {
        HashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
        for(int i = 0; i < keysAndValues.length; i += 2)
            map.put(keysAndValues[i], keysAndValues[i + 1]);
        return map;
    }
    
    @Test
    public void testIsNotEmpty() {
        Check.that(asMap(1, 1)).isNotEmpty();
        Check.that(asMap(1, 1, 2, 2, 3, 3)).isNotEmpty();
        Check.that(asMap(1, 1)).isNullOr().isNotEmpty();
        Check.that((Map<?, ?>) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(asMap()).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_EMPTY, false, defaultArgName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(asMap("a", 1)).not().isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_EMPTY, true, defaultArgName(), "{a=1}"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Map<?, ?>) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasSize() {
        Check.that(asMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)).hasSize(5);
        Check.that(asMap(1, 1, 1, 1)).hasSize(1); // duplicate key 1
        Check.that(asMap()).hasSize(0);
        
        Exception thrown = null;
        try {
            Check.that(asMap(1, 1)).hasSize(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_SIZE, false, defaultArgName(), 2, asMap(1, 1)),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Map<?, ?>) null).hasSize(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasSizeBetween() {
        Check.that(asMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)).hasSizeBetween(5, 5);
        Check.that(asMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)).hasSizeBetween(0, 10);
        Check.that(asMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5))
                .hasSizeBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(asMap()).hasSizeBetween(0, 0);
        Check.that(asMap()).hasSizeBetween(-1, 1);
        Check.that(asMap()).hasSizeBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(asMap(1, 1, 2, 2, 3, 3)).hasSizeBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_SIZE_BETWEEN, false, defaultArgName(), 0, 2,
                asMap(1, 1, 2, 2, 3, 3)), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(asMap(1, 1, 2, 2, 3, 3)).hasSizeBetween(4, Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_SIZE_BETWEEN, false, defaultArgName(), 4, Integer.MAX_VALUE,
                asMap(1, 1, 2, 2, 3, 3)), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Map<?, ?>) null).hasSizeBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasSizeWhich() {
        Check.that(asMap(1, 1, 2, 2, 3, 3)).hasSizeWhich().is(3);
        assertEquals("the size of " + Config.getConfig().getDefaultArgumentName(),
                Check.that(asMap(1, 1, 2, 2, 3, 3)).hasSizeWhich().argName);
        assertEquals("the size of the map", Check.that(asMap(1, 1, 2, 2, 3, 3)).named(
                "the map").hasSizeWhich().argName);
        
        Check.that((Map<?, ?>) null).isNullOr().hasSizeWhich().is(100);
        
        Exception thrown = null;
        try {
            Check.that((Map<?, ?>) null).hasSizeWhich();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContainsNoNullKey() {
        Check.that(asMap("hello", 1, "world", 2)).containsNoNullKey();
        Check.that(asMap("hello", 1)).containsNoNullKey();
        Check.that(asMap()).containsNoNullKey();
        
        Check.that((Map<?, ?>) null).isNullOr().containsNoNullKey();
        
        Exception thrown = null;
        try {
            Check.that(asMap("a", 1, "b", 2, null, 3)).containsNoNullKey();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_CONTAINS_NULL_KEY, false, defaultArgName(),
                "{a=1, b=2, null=3}"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Map<?, ?>) null).containsNoNullKey();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testContainsNoNullValues() {
        Check.that(asMap("hello", 1, "world", 2)).containsNoNullValues();
        Check.that(asMap("hello", 1)).containsNoNullValues();
        Check.that(asMap()).containsNoNullValues();
        
        Check.that((Map<?, ?>) null).isNullOr().containsNoNullValues();
        
        Exception thrown = null;
        try {
            Check.that(asMap("a", 1, "b", 2, "c", null)).containsNoNullValues();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_CONTAINS_NULL_VALUES, false, defaultArgName(),
                "{a=1, b=2, c=null}"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(asMap("a", null, "b", null, "c", null)).containsNoNullValues();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_CONTAINS_NULL_VALUES, false, defaultArgName(),
                "{a=null, b=null, c=null}"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((Map<?, ?>) null).containsNoNullValues();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(formatMsg(ARG_NULL, false, defaultArgName()), thrown.getMessage());
    }
}
