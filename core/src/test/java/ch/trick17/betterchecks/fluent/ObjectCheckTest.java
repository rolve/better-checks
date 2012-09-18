package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Config;
import ch.trick17.betterchecks.MessageFormatId;
import ch.trick17.betterchecks.MsgFormatter;

public class ObjectCheckTest {
    
    @Test
    public void testIsNullOr() {
        Check.that(new Object()).isNullOr().isNotNull();
        Check.that((Object) null).isNullOr().isNotNull();
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
        assertEquals(MsgFormatter.formatMsg(MessageFormatId.ARG_NULL, Config
                .getConfig().getDefaultArgumentName()), thrown.getMessage());
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
        assertEquals(
                MsgFormatter.formatMsg(MessageFormatId.ARG_NULL, "my arg"),
                thrown.getMessage());
    }
}
