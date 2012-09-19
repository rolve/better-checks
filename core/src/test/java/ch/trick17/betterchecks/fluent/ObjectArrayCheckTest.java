package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.MsgFormatId;
import ch.trick17.betterchecks.MsgFormatter;

public class ObjectArrayCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testIsNotEmpty() {
        Check.that(new String[] {"", ""}).isNotEmpty();
        Check.that(new String[] {""}).isNotEmpty();
        Check.that(new String[] {"", ""}).isNullOr().isNotEmpty();
        Check.that((String) null).isNullOr().isNotEmpty();
        
        Exception thrown = null;
        try {
            Check.that(new String[] {}).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_EMPTY, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((String[]) null).isNotEmpty();
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_NULL, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLength() {
        Check.that(new String[] {"", "", "", "", ""}).hasLength(5);
        Check.that(new String[] {"", ""}).hasLength(2);
        Check.that(new String[] {}).hasLength(0);
        
        Exception thrown = null;
        try {
            Check.that(new String[] {"A", "B", "C"}).hasLength(2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH, Config
                .getConfig().getDefaultArgumentName(), 2, "[A, B, C]"), thrown
                .getMessage());
    }
    
    @Test
    @SuppressWarnings("null")
    public void testHasLengthBetween() {
        Check.that(new String[] {"", "", "", "", ""}).hasLengthBetween(5, 5);
        Check.that(new String[] {"", "", "", "", ""}).hasLengthBetween(0, 10);
        Check.that(new String[] {"", "", "", "", ""}).hasLengthBetween(
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        Check.that(new String[] {}).hasLengthBetween(0, 0);
        Check.that(new String[] {}).hasLengthBetween(-1, 1);
        Check.that(new String[] {}).hasLengthBetween(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        
        Exception thrown = null;
        try {
            Check.that(new String[] {"a", "b", "c"}).hasLengthBetween(0, 2);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(
                MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN, Config
                        .getConfig().getDefaultArgumentName(), 0, 2,
                        "[a, b, c]"), thrown.getMessage());
        
        thrown = null;
        try {
            Check.that(new String[] {"1", "2", "3"}).hasLengthBetween(4,
                    Integer.MAX_VALUE);
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(MsgFormatter.formatMsg(MsgFormatId.ARG_LENGTH_BETWEEN,
                Config.getConfig().getDefaultArgumentName(), 4,
                Integer.MAX_VALUE, "[1, 2, 3]"), thrown.getMessage());
    }
}