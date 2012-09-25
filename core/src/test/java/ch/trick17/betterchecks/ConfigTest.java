package ch.trick17.betterchecks;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class ConfigTest {
    
    @Test
    public void testGetDefaultArgumentName() {
        // TODO: Find a way to test with and without config file
        assertEquals("Your argument", Config.getConfig()
                .getDefaultArgumentName());
    }
    
    @Test
    public void testGetMessageFormat() {
        /* Default formats */
        assertEquals("%s must not be null", Config.getConfig()
                .getMessageFormat(MessageType.ARG_NULL, false));
        assertEquals("%s must be null", Config.getConfig().getMessageFormat(
                MessageType.ARG_NULL, true));
        
        /* Overwritten in config file */
        assertEquals("%s should be positive", Config.getConfig()
                .getMessageFormat(MessageType.ARG_POSITIVE, false));
        assertEquals("%s should not be negative", Config.getConfig()
                .getMessageFormat(MessageType.ARG_NEGATIVE, true));
    }
}
